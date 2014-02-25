package de.jworks.datahub.business.datasets.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class Attribute extends Item {
	
	@XmlAttribute
	private AttributeType type;
	
	@XmlAttribute
	private String constraint;
	
	public Attribute() {
	}

	public Attribute(String name, AttributeType valueType, String valueConstraint) {
		super(name);
		this.type = valueType;
		this.constraint = valueConstraint;
	}

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType valueType) {
		this.type = valueType;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String valueConstraint) {
		this.constraint = valueConstraint;
	}
	
}
