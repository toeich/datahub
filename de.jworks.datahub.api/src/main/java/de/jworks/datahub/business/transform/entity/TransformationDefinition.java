package de.jworks.datahub.business.transform.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transformation-definition")
@XmlAccessorType(XmlAccessType.NONE)
public class TransformationDefinition extends Notifier {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private Datasource datasource;
	
	@XmlElement
	private Datasink datasink;

	@XmlElementWrapper(name = "components")
	@XmlElement(name = "component")
	private Set<TransformationComponent> components = new LinkedHashSet<TransformationComponent>();

	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link")
	private Set<Link> links = new LinkedHashSet<Link>();
	
	// uri -> output
	private transient Map<String, Output> _outputs = new HashMap<String, Output>();

	// output -> uri
	private transient Map<Output, String> _outputUris = new HashMap<Output, String>();

	// uri -> input
	private transient Map<String, Input> _inputs = new HashMap<String, Input>();

	// input -> uri
	private transient Map<Input, String> _inputUris = new HashMap<Input, String>();
	
	// output -> component
	private transient Map<Item, Component> _components = new HashMap<Item, Component>();
	
	public Datasource getDatasource() {
		return datasource;
	}
	
	public void setDatasource(Datasource datasource) {
		this.datasource = datasource;
	}
	
	public Datasink getDatasink() {
		return datasink;
	}
	
	public void setDatasink(Datasink datasink) {
		this.datasink = datasink;
	}
	
	public Set<TransformationComponent> getComponents() {
		return Collections.unmodifiableSet(components);
	}

	public void addComponent(TransformationComponent component) {
		components.add(component);
		firePropertyChange("components", null, null);
	}

	public void removeComponent(TransformationComponent component) {
		components.remove(component);
		firePropertyChange("components", null, null);
	}

	public Set<Link> getLinks() {
		return Collections.unmodifiableSet(links);
	}

	public void addLink(Link link) {
		links.add(link);
		firePropertyChange("links", null, null);
	}

	public void removeLink(Link link) {
		links.remove(link);
		firePropertyChange("links", null, null);
	}
	
//	public Map<Item, String> getUris() {
//		if (_uris == null) {
//			_uris = new HashMap<Item, String>();
//		}
//		return _uris;
//	}
//	
//	public String getUri(Item item) {
//		return getUris().get(item);
//	}
//	
//	public Map<String, Item> getItems() {
//		if (_items == null) {
//			_items = new HashMap<String, Item>();
//		}
//		return _items;
//	}
//	
//	public Item getItem(String uri) {
//		return getItems().get(uri);
//	}
//	
//	private void collectOutputs() {
//		if (datasource != null) {
//			collectOutputs(datasource);
//		}
//		for (TransformationComponent component : components) {
//			collectOutputs(component);
//		}
//	}
//
//	private void collectOutputs(Component component) {
//		String componentUri = component.getId() + ":";
//		for (Output output : component.getSchema().getOutputs()) {
//			collectOutputs(output, component, componentUri);
//		}
//	}
//
//	private void collectOutputs(Output output, Component component, String parentUri) {
//		String step = step(output);
//		String outputUri = parentUri + "/" + step;
//		_items.put(outputUri, output);
//		_uris.put(output, outputUri);
//		_components.put(output, component);
//		for (Output o : output.getOutputs()) {
//			collectOutputs(o, component, outputUri);
//		}
//	}
//
//	private void collectInputs() {
//		Datasink datasink = transformationDefinition.getDatasink();
//		if (datasink != null) {
//			collectInputs(datasink);
//		}
//		for (TransformationComponent component : transformationDefinition.getComponents()) {
//			collectInputs(component);
//		}
//	}
//
//	private void collectInputs(Component component) {
//		String componentUri = component.getId() + ":";
//		for (Input input : component.getSchema().getInputs()) {
//			collectInputs(input, componentUri);
//		}
//	}
//
//	private void collectInputs(Input input, String parentUri) {
//		String step = step(input);
//		if (step.startsWith("{")) {
//			String namespaceUri = step.substring(1, step.indexOf("}"));
//			if (!namespaceUris.contains(namespaceUri)) {
//				namespaceUris.add(namespaceUri);
//			}
//		}
//		String inputUri = parentUri + "/" + step;
//		inputs.put(inputUri, input);
//		inputUris.put(input, inputUri);
//		for (Input i : input.getInputs()) {
//			collectInputs(i, inputUri);
//		}
//	}
//	
//	private String step(Item item) {
//		return (item.getType() == ItemType.XML_ATTRIBUTE ? "@" : "") + item.getName();
//	}
	
}