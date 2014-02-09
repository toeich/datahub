package de.jworks.datahub.business.documents.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "schema")
@XmlAccessorType(XmlAccessType.NONE)
public class DatasetSchema {

	@XmlElement(name = "root-element")
	private Element rootElement = new Element("root-element");

	public Element getRootElement() {
		return rootElement;
	}
	
	public void updateParents() {
		setParent(rootElement, null);
	}

	private void setParent(Item item, Element parent) {
		item.setParent(parent);
		if (item instanceof Element) {
			Element element = (Element) item;
			for (Attribute a : element.getAttributes()) {
				setParent(a, element);
			}
			for (Element e : element.getElements()) {
				setParent(e, element);
			}
		}
	}
	
}
