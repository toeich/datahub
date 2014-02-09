package de.jworks.datahub.business.documents.controller;

import de.jworks.datahub.business.documents.entity.Attribute;
import de.jworks.datahub.business.documents.entity.Element;


public class SchemaGenerator {

	public String generate(Element e) {
		StringBuilder builder = new StringBuilder();
		builder.append("<xsd:schema xmlns:xsd='http://www.w3.org/2001/XMLSchema'>");
		processRootElement(e, builder);
		builder.append("</xsd:schema>");
		return builder.toString();
	}

	private void processRootElement(Element e, StringBuilder builder) {
		if (e.getAttributes().isEmpty() && e.getElements().isEmpty()) {
			builder.append("<xsd:element");
			builder.append(" name='" + e.getName() + "'");
			builder.append(" type='xsd:string'");
			builder.append("/>");
		} else {
			builder.append("<xsd:element");
			builder.append(" name='" + e.getName() + "'");
			builder.append(">");
			builder.append("<xsd:complexType>");
			if (e.getElements().size() > 0) {
				builder.append("<xsd:sequence>");
				for (Element element : e.getElements()) {
					processElement(element, builder);
				}
				builder.append("</xsd:sequence>");
			}
			for (Attribute attribute : e.getAttributes()) {
				processAttribute(attribute, builder);
			}
			builder.append("</xsd:complexType>");
			builder.append("</xsd:element>");
		}
	}

	private void processElement(Element e, StringBuilder builder) {
		if (e.getAttributes().isEmpty() && e.getElements().isEmpty()) {
			builder.append("<xsd:element");
			builder.append(" name='" + e.getName() + "'");
			builder.append(" type='xsd:string'");
			builder.append(" minOccurs='" + (e.getMinOccurs() != null ? e.getMinOccurs() : 0) + "'");
			builder.append(" maxOccurs='" + (e.getMaxOccurs() != null ? e.getMaxOccurs() : "unbounded") + "'");
			builder.append("/>");
		} else {
			builder.append("<xsd:element");
			builder.append(" name='" + e.getName() + "'");
			builder.append(" minOccurs='" + (e.getMinOccurs() != null ? e.getMinOccurs() : 0) + "'");
			builder.append(" maxOccurs='" + (e.getMaxOccurs() != null ? e.getMaxOccurs() : "unbounded") + "'");
			builder.append(">");
			builder.append("<xsd:complexType>");
			if (e.getElements().size() > 0) {
				builder.append("<xsd:sequence>");
				for (Element element : e.getElements()) {
					processElement(element, builder);
				}
				builder.append("</xsd:sequence>");
			}
			for (Attribute attribute : e.getAttributes()) {
				processAttribute(attribute, builder);
			}
			builder.append("</xsd:complexType>");
			builder.append("</xsd:element>");
		}
	}

	private void processAttribute(Attribute a, StringBuilder builder) {
		builder.append("<xsd:attribute");
		builder.append(" name='" + a.getName() + "'");
		builder.append(" type='xsd:string'");
		builder.append("/>");
	}

}