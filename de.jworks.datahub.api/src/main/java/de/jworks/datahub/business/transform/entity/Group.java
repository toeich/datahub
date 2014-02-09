package de.jworks.datahub.business.transform.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Group {

	@XmlAttribute
	private String name;

	@XmlElement(name = "component")
	private List<TransformationComponent> components = new ArrayList<TransformationComponent>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TransformationComponent> getComponents() {
		return components;
	}
}
