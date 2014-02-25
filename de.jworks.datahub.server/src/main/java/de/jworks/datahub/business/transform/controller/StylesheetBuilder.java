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

public class StylesheetBuilder {
	
	private static final Logger logger = Logger.getLogger(StylesheetBuilder.class.getName()); 

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
		String stylesheet = buildStylesheet(transformation.getDefinition());
		logger.info("stylesheet of transformation '" + transformation + "':\n" + stylesheet);
		return stylesheet;
	}
	
	public static String buildStylesheet(TransformationDefinition transformationDefinition) {
		StylesheetBuilder builder = new StylesheetBuilder(transformationDefinition);
		String stylesheet = builder.buildStylesheet();
		return stylesheet;
	}

	private StylesheetBuilder(TransformationDefinition transformationDefinition) {
		this.transformationDefinition = transformationDefinition;
	}

	private String buildStylesheet() {
		collectOutputs();
		collectInputs();
		collectSources();
		
		addMissingSources();

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

	private void addMissingSources() {
		addMissingSources(transformationDefinition.getDatasink());
	}

	private void addMissingSources(Datasink datasink) {
		for (Input input : datasink.getSchema().getInputs()) {
			addMissingSources(input, null);
		}
	}

	private boolean addMissingSources(Input target, Output parentSource) {
		Output source = sources.get(target);
		
		boolean mapped = false;
		for (Input input : target.getInputs()) {
			boolean childMapped = addMissingSources(input, source != null ? source : parentSource);
			if (childMapped) {
				mapped = true;
			}
		}
		
		if (source == null && mapped) {
			if (parentSource == null) {
				throw new RuntimeException();
			}
			sources.put(target, parentSource);
		}
		
		return mapped;
	}

	private void process(Datasink datasink) {
		builder.append("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n");
		
		builder.append("<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'");
		for (int i = 0; i < namespaceUris.size(); i++) {
			builder.append(" xmlns:ns" + i + "='" + namespaceUris.get(i) + "'");
		}
		builder.append(">\n");
		
		builder.append("<xsl:output method='xml' omit-xml-declaration='yes' indent='yes' />\n\n");

		builder.append("<xsl:template match='/'>\n");
		for (Input i : datasink.getSchema().getInputs()) {
			builder.append("<xsl:call-template name='" + templateName(i) + "' />\n");
		}
		builder.append("</xsl:template>\n\n");

		for (Input i : datasink.getSchema().getInputs()) {
			processInput(i, null);
		}

		builder.append("</xsl:stylesheet>\n");
	}

	private void processInput(Input input, Output context) {
		builder.append("<xsl:template name='" + templateName(input) + "'>\n");
		
		if (sources.get(input) != null) {
			builder.append("<xsl:for-each select='" + expr(input, context) + "'>\n");
		}

		switch (input.getType()) {
		case XML_ELEMENT:
			builder.append("<xsl:element name='" + elementName(input) + "'>\n");
			break;
		case XML_ATTRIBUTE:
			builder.append("<xsl:attribute name='" + attributeName(input) + "'>\n");
			break;
		default:
			throw new RuntimeException("unsupported type: " + input.getType());
		}

		if (input.getInputs().size() > 0) {
			for (Input i : input.getInputs()) {
//				if (sources.get(i) != null) {
					builder.append("<xsl:call-template name='" + templateName(i) + "' />\n");
//				}
			}
		} else {
			builder.append("<xsl:value-of select='.' />\n");
		}

		switch (input.getType()) {
		case XML_ELEMENT:
			builder.append("</xsl:element>\n");
			break;
		case XML_ATTRIBUTE:
			builder.append("</xsl:attribute>\n");
			break;
		default:
			throw new RuntimeException("unsupported type: " + input.getType());
		}

		if (sources.get(input) != null) {
			builder.append("</xsl:for-each>\n");
		}
		
		builder.append("</xsl:template>\n\n");

		for (Input i : input.getInputs()) {
//			if (sources.get(i) != null) {
				processInput(i, context(input));
//			}
		}
	}

	private String expr(Input input, Output context) {
		Output source = sources.get(input);
		
		Component sourceComponent = components.get(source);

		if (sourceComponent instanceof Datasource) {
			return datasourceExpr(source, context);
		}
		if (sourceComponent instanceof Constant) {
			return constantExpr(source, context);
		}
		if (sourceComponent instanceof Function) {
			return functionExpr(source, context);
		}
		if (sourceComponent instanceof Operation) {
			return operationExpr(source, context);
		}
		if (sourceComponent instanceof Filter) {
			return filterExpr(source, context);
		}
		if (sourceComponent instanceof Lookup) {
			return lookupExpr(source, context);
		}
		
		throw new RuntimeException("unable to compute select expression" + input);
	}

	private String datasourceExpr(Output source, Output context) {
		return relativePath(source, context);
	}
	
	private String constantExpr(Output source, Output context) {
		Constant constant = (Constant) components.get(source);
		return constant.getValue();
	}

	private String functionExpr(Output source, Output context) {
		Function function = (Function) components.get(source);
		
		Output functionContext = context(function.getSchema().getInputs());
		
		StringBuilder builder = new StringBuilder();
		if (functionContext != null) {
			builder.append(relativePath(functionContext, context));
			builder.append("/");
		}
		builder.append(function.getXpathFunction());
		builder.append("(");
		for (Input i : function.getSchema().getInputs()) {
			if (i.getType() == ItemType.CONTEXT) continue;
			builder.append(expr(i, functionContext) + ",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");		
		return builder.toString();
	}
	
	private String operationExpr(Output source, Output context) {
		Operation operation = (Operation) components.get(source);
		
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		for (Input input : operation.getSchema().getInputs()) {
			builder.append(expr(input, context));
			builder.append(" " + operation.getOperator() + " ");
		}
		builder.delete(builder.length() - operation.getOperator().length() - 2, builder.length());
		builder.append(")");
		
		return builder.toString();
	}
	
	private String filterExpr(Output source, Output context) {
		Filter filter = (Filter) components.get(source);
		
		Output filterContext = context(filter.getSchema().getInputs().get(0));
		
		StringBuilder builder = new StringBuilder();
		builder.append(relativePath(filterContext, context));
		builder.append("[");
		builder.append(expr(filter.getSchema().getInputs().get(1), filterContext));
		builder.append("]");
		
		return builder.toString();
	}

	private String lookupExpr(Output source, Output context) {
		Lookup lookup = (Lookup) components.get(source);
		
		if (components.get(context) == lookup) {
			return relativePath(source, context);
		}
		
		Output lookupContext = context(lookup.getSchema().getInputs());
		
		StringBuilder builder = new StringBuilder();
		if (lookupContext != null) {
			builder.append(relativePath(lookupContext, context));
			builder.append("/");
		}
		builder.append("document(concat(\"\", \"" + lookup.getDatasourceSpec() + "\"");
		if (lookup.getSchema().getInputs().size() > 0) {
			builder.append(",\"?");
			for (Input input : lookup.getSchema().getInputs()) {
				builder.append(step(input));
				builder.append("=\"");
				builder.append(",");
				builder.append(expr(input, lookupContext));
				builder.append(",\"&\"");
			}
			builder.delete(builder.length() - 4, builder.length());
		}
		builder.append("))");
		builder.append(absolutePath(source));
		
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

	private String relativePath(Output source, Output context) {
		if (context == null) {
			return absolutePath(source);
		}
		if (source == context) {
			return ".";
		}
		if (components.get(source) != components.get(context)) {
			return absolutePath(source);
		}

		List<String> sourcePath = new ArrayList<String>(Arrays.asList(outputUris.get(source).split("/")));
		List<String> outputPath = new ArrayList<String>(Arrays.asList(outputUris.get(context).split("/")));
		while (sourcePath.size() > 0 && outputPath.size() > 0 && sourcePath.get(0).equals(outputPath.get(0))) {
			sourcePath.remove(0);
			outputPath.remove(0);
		}

		StringBuffer relativePath = new StringBuffer();
		for (int i = 0; i < outputPath.size(); i++) {
			relativePath.append("../");
		}
		for (String s : sourcePath) {
			relativePath.append(s + "/");
		}

		return normalize(relativePath.substring(0, relativePath.length() - 1).toString());
	}

	private String absolutePath(Output source) {
		String uri = outputUris.get(source);
		String path  = uri.substring(uri.indexOf(":") + 1);
		return path;
	}
	
	private String templateName(Input input) {
		return inputUris.get(input).replaceAll("\\W", "_");
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
