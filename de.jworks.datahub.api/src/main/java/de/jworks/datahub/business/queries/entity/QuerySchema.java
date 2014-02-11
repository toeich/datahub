package de.jworks.datahub.business.queries.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.jworks.datahub.business.documents.entity.Element;

@XmlRootElement(name = "query-schema")
@XmlAccessorType(XmlAccessType.NONE)
public class QuerySchema {

	@XmlElement
	private Element element = new Element("element");

	public Element getElement() {
		return element;
	}
	
}
