package de.jworks.datahub.business.datasets.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public class Attribute extends Item {
	
	public Attribute() {
	}

	public Attribute(String name) {
		super(name);
	}
	
}
