package de.jworks.datahub.business.datasets.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.NONE)
public class Element extends Item {
	
	@XmlAttribute
	protected String pluralLabel;
	
	@XmlAttribute
	protected Integer minOccurs;
	
	@XmlAttribute
	protected Integer maxOccurs;

	@XmlElementWrapper(name = "attributes")
	@XmlElement(name = "attribute")
	protected List<Attribute> attributes = new ArrayList<Attribute>();
	
	@XmlElementWrapper(name = "elements")
	@XmlElement(name = "element")
	protected List<Element> elements = new ArrayList<Element>();
	
	public Element() {
	}

	public Element(String name) {
		super(name);
		minOccurs = 1;
		maxOccurs = 1;
	}

	public String getPluralLabel() {
		return pluralLabel;
	}

	public void setPluralLabel(String pluralLabel) {
		this.pluralLabel = pluralLabel;
	}

	public Integer getMinOccurs() {
		return minOccurs;
	}

	public void setMinOccurs(Integer minOccurs) {
		this.minOccurs = minOccurs;
	}

	public Integer getMaxOccurs() {
		return maxOccurs;
	}

	public void setMaxOccurs(Integer maxOccurs) {
		this.maxOccurs = maxOccurs;
	}

	public List<Attribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}
	
	public Attribute addAttribute(String name, AttributeType valueType, String valueConstraint) {
		Attribute attribute = new Attribute(name, valueType, valueConstraint);
		attribute.setParent(this);
		attributes.add(attribute);
		return attribute;
	}
	
	public void removeAttribute(Item attribute) {
		if (attributes.remove(attribute)) {
			attribute.setParent(null);
		}
	}
	
	public List<Element> getElements() {
		return Collections.unmodifiableList(elements);
	}
	
	public Element addElement(String name) {
		Element element = new Element(name);
		element.setParent(this);
		elements.add(element);
		return element;
	}
	
	public void removeElement(Element element) {
		if (elements.remove(element)) {
			element.setParent(null);
		}
	}

}
