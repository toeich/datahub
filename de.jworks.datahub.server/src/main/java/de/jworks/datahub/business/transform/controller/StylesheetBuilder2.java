
package de.jworks.datahub.business.transform.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.jworks.datahub.business.transform.entity.Component;
import de.jworks.datahub.business.transform.entity.Constant;
import de.jworks.datahub.business.transform.entity.Datasink;
import de.jworks.datahub.business.transform.entity.Datasource;
import de.jworks.datahub.business.transform.entity.Filter;
import de.jworks.datahub.business.transform.entity.Function;
import de.jworks.datahub.business.transform.entity.Input;
import de.jworks.datahub.business.transform.entity.Item;
import de.jworks.datahub.business.transform.entity.ItemType;
import de.jworks.datahub.business.transform.entity.Link;
import de.jworks.datahub.business.transform.entity.Lookup;
import de.jworks.datahub.business.transform.entity.Operation;
import de.jworks.datahub.business.transform.entity.Output;
import de.jworks.datahub.business.transform.entity.Transformation;
import de.jworks.datahub.business.transform.entity.TransformationComponent;
import de.jworks.datahub.business.transform.entity.TransformationDefinition;

public class StylesheetBuilder2 {
	
	private static final Logger logger = Logger.getLogger(StylesheetBuilder2.class.getName()); 

	private TransformationDefinition transformationDefinition;

	// uri -> output
	private Map<String, Output> outputs = new HashMap<String, Output>();

	// output -> uri
	private Map<Output, String> outputUris = new HashMap<Output, String>();

	// output -> component
	private Map<Output, Component> components = new HashMap<Output, Component>();

	// uri -> input
	private Map<String, Input> inputs = new HashMap<String, Input>();

	// input -> uri
	private Map<Input, String> inputUris = new HashMap<Input, String>();

	// target (input) -> source (output)
	private Map<Input, Output> sources = new HashMap<Input, Output>();

	// namespace uris
	private List<String> namespaceUris = new ArrayList<String>();

	private StringBuilder builder = new StringBuilder();

	public static String buildStylesheet(Transformation transformation) {
		return buildStylesheet(transformation.getDefinition());
	}
	
	public static String buildStylesheet(TransformationDefinition transformationDefinition) {
		StylesheetBuilder2 builder = new StylesheetBuilder2(transformationDefinition);
		String stylesheet = builder.buildStylesheet();
		System.out.println(stylesheet);
		return stylesheet;
	}

	private StylesheetBuilder2(TransformationDefinition transformationDefinition) {
		this.transformationDefinition = transformationDefinition;
	}

	private String buildStylesheet() {
		collectOutputs();
		collectInputs();
		collectSources();
		
		process(transformationDefinition.getDatasink());

		String stylesheet = builder.toString();

		return stylesheet;
	}

	private void collectOutputs() {
		Datasource datasource = transformationDefinition.getDatasource();
		if (datasource != null) {
			collectOutputs(datasource);
		}
		for (TransformationComponent component : transformationDefinition.getComponents()) {
			collectOutputs(component);
		}
	}

	private void collectOutputs(Component component) {
		String componentUri = component.getName() + ":";
		for (Output output : component.getSchema().getOutputs()) {
			collectOutputs(output, component, componentUri);
		}
	}

	private void collectOutputs(Output output, Component component, String parentUri) {
		String step = step(output);
		if (step.startsWith("{")) {
			String namespaceUri = step.substring(1, step.indexOf("}"));
			if (!namespaceUris.contains(namespaceUri)) {
				namespaceUris.add(namespaceUri);
			}
		}
		String outputUri = parentUri + "/" + step;
		outputs.put(outputUri, output);
		outputUris.put(output, outputUri);
		components.put(output, component);
		for (Output o : output.getOutputs()) {
			collectOutputs(o, component, outputUri);
		}
	}

	private void collectInputs() {
		Datasink datasink = transformationDefinition.getDatasink();
		if (datasink != null) {
			collectInputs(datasink);
		}
		for (TransformationComponent component : transformationDefinition.getComponents()) {
			collectInputs(component);
		}
	}

	private void collectInputs(Component component) {
		String componentUri = component.getName() + ":";
		for (Input input : component.getSchema().getInputs()) {
			collectInputs(input, componentUri);
		}
	}

	private void collectInputs(Input input, String parentUri) {
		String step = step(input);
		if (step.startsWith("{")) {
			String namespaceUri = step.substring(1, step.indexOf("}"));
			if (!namespaceUris.contains(namespaceUri)) {
				namespaceUris.add(namespaceUri);
			}
		}
		String inputUri = parentUri + "/" + step;
		inputs.put(inputUri, input);
		inputUris.put(input, inputUri);
		for (Input i : input.getInputs()) {
			collectInputs(i, inputUri);
		}
	}

	private void collectSources() {
		for (Link link : transformationDefinition.getLinks()) {
			Output output = outputs.get(link.getSource());
			if (output == null) {
				throw new RuntimeException("source of link not found: " + link.getSource());
			}
			Input input = inputs.get(link.getTarget());
			if (input == null) {
				throw new RuntimeException("target of link not found: " + link.getTarget());
			}
			sources.put(input, output);
		}
	}

	private void process(Datasink datasink) {
		builder.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n");
		
		builder.append("<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'");
		for (int i = 0; i < namespaceUris.size(); i++) {
			builder.append(" xmlns:ns" + i + "='" + namespaceUris.get(i) + "'");
		}
		builder.append(">\n");
		
		builder.append(" <xsl:output method='xml' omit-xml-declaration='yes' indent='yes' />\n");

		builder.append(" <xsl:template match='/'>\n");
		for (Input i : datasink.getSchema().getInputs()) {
			processInput(i, Arrays.asList((Output) null), 1);
		}
		builder.append(" </xsl:template>\n");

		builder.append("</xsl:stylesheet>\n");
	}

	private void processInput(Input input, List<Output> context, int indent) {
		List<Output> newContext = new ArrayList<Output>(context);
		
		logger.fine("processing Input '" + inputUris.get(input) + dump(newContext));
		
		String prefix = ""; for (int i = 0; i < indent; i++) { prefix+=" "; }

		String xpath = null;
		Output output = sources.get(input);
		if (output != null) {
			xpath = processOutput(output, newContext, indent+1);
			if (xpath != null) {
				builder.append(prefix + "<xsl:for-each select='" + xpath + "'>\n");
			}
			newContext.add(output);
		}
		
		if (input.getType() != null) {
			switch (input.getType()) {
			case XML_ELEMENT:
				builder.append(prefix + " <xsl:element name='" + elementName(input) + "'>\n");
				break;
			case XML_ATTRIBUTE:
				builder.append(prefix + " <xsl:attribute name='" + attributeName(input) + "'>\n");
				break;
			default:
				throw new RuntimeException("unsupported type: " + input.getType());
			}
		}
		
		if (input.getInputs().size() > 0) {
			for (Input i : input.getInputs()) {
				processInput(i, newContext, indent+1);
			} 
		} else {
			builder.append(prefix + "  <xsl:value-of select='.' />\n");
		}
		
		if (input.getType() != null) {
			switch (input.getType()) {
			case XML_ELEMENT:
				builder.append(prefix + " </xsl:element>\n");
				break;
			case XML_ATTRIBUTE:
				builder.append(prefix + " </xsl:attribute>\n");
				break;
			default:
				throw new RuntimeException("unsupported type: " + input.getType());
			}
		}
		
		if (xpath != null) {
			builder.append(prefix + "</xsl:for-each>\n");
		}
	}
	
	private String dump(List<Output> context) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < context.size(); i++) {
			String outputUri = outputUris.get(context.get(i));
			if (outputUri != null) {
				buffer.append("\n\t" + outputUri);
			}
		}
		return buffer.toString();
	}
	
	private String processOutput(Output output, List<Output> context, int indent) {
		logger.info("processing Output '" + outputUris.get(output) + " of " + components.get(output).getClass().getSimpleName() + dump(context));
		
		Component component = components.get(output);
		if (component instanceof Datasource) {
			return processDatasourceOutput(output, context);
		}
		if (component instanceof Lookup) {
			return processLookupOutput(output, context);
		}
		if (component instanceof Constant) {
			return processConstantOutput(output, context);
		}
		if (component instanceof Function) {
			return processFunctionOutput(output, context);
		}
		if (component instanceof Operation) {
			return processOperationOutput(output, context);
		}
		if (component instanceof Filter) {
			return processFilterOutput(output, context);
		}
		throw new RuntimeException();
	}
	
	private String processDatasourceOutput(Output output, List<Output> context) {
		return path(output, context);
	}
	
	private String processConstantOutput(Output output, List<Output> context) {
		Constant constant = (Constant) components.get(output);
		return constant.getValue();
	}

	private String processFunctionOutput(Output output, List<Output> context) {
		Function function = (Function) components.get(output);
		
		StringBuilder builder = new StringBuilder(); 
		
		Output baseOutput = context(function.getSchema().getInputs());
		logger.info("\tbase Output '"+outputUris.get(baseOutput)+"'");
		if (baseOutput != null) {
			builder.append(processOutput(baseOutput, context, 0));
			builder.append("/");
			context.add(0, baseOutput);
		}
	
		builder.append(function.getXpathFunction());
		builder.append("(");
		for (Input i : function.getSchema().getInputs()) {
			if (i.getType() == ItemType.CONTEXT) continue;
			builder.append(processOutput(sources.get(i), context, 0) + ",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");
		
		return builder.toString();
	}
	
	private String processOperationOutput(Output source, List<Output> context) {
		Operation operation = (Operation) components.get(source);
		
		StringBuilder builder = new StringBuilder();
		
		Output baseOutput = context(operation.getSchema().getInputs());
		if (baseOutput != null) {
			builder.append(processOutput(baseOutput, context, 0));
			builder.append("/");
			context.add(0, baseOutput);
		}
		
		builder.append("(");
		for (Input i : operation.getSchema().getInputs()) {
			builder.append(processOutput(sources.get(i), context, 0));
			builder.append(operation.getOperator());
		}
		builder.delete(builder.length() - operation.getOperator().length(), builder.length());
		builder.append(")");
		
		return builder.toString();
	}
	
	private String processFilterOutput(Output source, List<Output> context) {
		Filter filter = (Filter) components.get(source);
		
		StringBuilder builder = new StringBuilder();
		
		Output baseOutput = sources.get(filter.getSchema().getInputs().get(0));
		builder.append(processOutput(baseOutput, context, 0));
		context.add(baseOutput);
		
		List<Output> context2 = new ArrayList<Output>(context);
		
		builder.append("[");
		builder.append(processOutput(sources.get(filter.getSchema().getInputs().get(1)), context2, 0));
		builder.append("]");
		
		return builder.toString();
	}

	private String processLookupOutput(Output output, List<Output> context) {
		Lookup lookup = (Lookup) components.get(output);
		
		String relativePath = relativePath(output, context);
		if (relativePath != null) {
			return relativePath;
		}
		
		StringBuilder builder = new StringBuilder();
		
		Output base = context(lookup.getSchema().getInputs());
		logger.info("\tbase Output '" + outputUris.get(base) + "'");
		if (base != null) {
			builder.append(processOutput(base, context, 0));
			builder.append("/");
			context.add(0, base);
		}

		builder.append("document(concat(\"\", \"" + lookup.getDatasourceSpec() + "\"");
		if (lookup.getSchema().getInputs().size() > 0) {
			builder.append(",\"?");
			for (Input input : lookup.getSchema().getInputs()) {
				builder.append(step(input));
				builder.append("=\"");
				builder.append(",");
				builder.append(processOutput(sources.get(input), context, 0));
				builder.append(",\"&\"");
			}
			builder.delete(builder.length() - 4, builder.length());
		}
		builder.append("))");
		
		builder.append(absolutePath(output));
		
		return builder.toString();
	}

	private Output context(List<Input> inputs) {
		Output context = null, primary = null, first = null;
		for (Input input : inputs) {
			Output output = context(input);
			if (input.getType() == ItemType.CONTEXT) {
				context = output;
			}
			if (input.getType() == ItemType.PRIMARY) {
				primary = output;
			}
			if (first == null) {
				first = output;
			}
		}
		if (context == null) {
			context = primary;
		}
		if (context == null) {
			context = first;
		}
		return context;
	}

	private Output context(Input target) {
		Output source = sources.get(target);
		Component sourceComponent = components.get(source);
		if (sourceComponent instanceof Datasource) {
			return source;
		}
		if (sourceComponent instanceof Function) {
			Function function = (Function) sourceComponent;
			return context(function.getSchema().getInputs());
		}
		if (sourceComponent instanceof Operation) {
			Operation operation = (Operation) sourceComponent;
			return context(operation.getSchema().getInputs());
		}
		if (sourceComponent instanceof Filter) {
			Filter filter = (Filter) sourceComponent;
			return context(filter.getSchema().getInputs());
		}
		if (sourceComponent instanceof Lookup) {
			return source;
		}
		return null;
	}

	private String elementName(Input input) {
		return normalize(input.getName());
	}

	private String attributeName(Input input) {
		return normalize(input.getName());
	}

	private String step(Item item) {
		if (item.getType() != null) {
			switch (item.getType()) {
			case XML_ELEMENT:
				return item.getName();
			case XML_ATTRIBUTE:
				return "@" + item.getName();
			default:
				return item.getName();
			}
		}
		return item.getName();
	}

	private String path(Output output, List<Output> context) {
		String relativePath = relativePath(output, context);
		if (relativePath != null) {
			return relativePath;
		}
		return absolutePath(output);
	}
	
	private String relativePath(Output output, List<Output> context) {
		for (Output c : context) {
			String relativePath = relativePath(output, c);
			if (relativePath != null) {
				return relativePath;
			}
		}
		return null;
	}
	
	private String relativePath(Output output, Output base) {
		if (components.get(output) != components.get(base)) {
			return null;
		}
		
		if (output == base) {
			return ".";
		}

		List<String> outputPath = new ArrayList<String>(Arrays.asList(outputUris.get(output).split("/")));
		List<String> contextPath = new ArrayList<String>(Arrays.asList(outputUris.get(base).split("/")));
		while (outputPath.size() > 0 && contextPath.size() > 0 && outputPath.get(0).equals(contextPath.get(0))) {
			outputPath.remove(0);
			contextPath.remove(0);
		}

		StringBuffer relativePath = new StringBuffer();
		for (int i = 0; i < contextPath.size(); i++) {
			relativePath.append("../");
		}
		for (String s : outputPath) {
			relativePath.append(s + "/");
		}

		return normalize(relativePath.substring(0, relativePath.length() - 1).toString());
	}
	
	private String absolutePath(Output output) {
		String uri = outputUris.get(output);
		String path  = uri.substring(uri.indexOf(":") + 1);
		return path;
	}
	
	private String normalize(String s) {
		StringBuilder builder = new StringBuilder(s);
		int startIdx = builder.indexOf("{");
		while (startIdx != -1) {
			int endIdx = builder.indexOf("}");
			String namespaceUri = builder.substring(startIdx + 1, endIdx);
			builder.replace(startIdx, endIdx + 1, "ns" + namespaceUris.indexOf(namespaceUri) + ":");
			startIdx = builder.indexOf("{");
		}
		return builder.toString();
	}
	
}
