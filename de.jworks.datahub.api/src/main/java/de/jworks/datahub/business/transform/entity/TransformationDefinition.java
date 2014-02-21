package de.jworks.datahub.business.transform.entity;

import java.util.Collections;
import java.util.LinkedHashSet;
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
}