package de.jworks.connector.transform.tools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SchemaGenerator {
	
	public static enum SchemaType { DATASINK, DATASOURCE };

	public static String generateSchemaFromXml(String xml, SchemaType schemaType) throws Exception {
		return generateSchema(new ByteArrayInputStream(xml.getBytes("UTF-8")), schemaType);
	}
	
	public static String generateSchemaFromUrl(String url, SchemaType schemaType) throws Exception {
		return generateSchema(new URL(url).openStream(), schemaType);
	}
	
	public static String generateSchema(InputStream inputStream, SchemaType schemaType) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();

		Node node = null;
		node = document.appendChild(document.createElement("schema"));
		node = node.appendChild(document.createElement(schemaType == SchemaType.DATASINK ? "inputs" : "outputs"));
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				if (node == null) {
					Element newElement = document.createElement(schemaType == SchemaType.DATASINK ? "input" : "output");
					newElement.setAttribute("name", reader.getLocalName());
					newElement.setAttribute("step", "" + reader.getName());
					newElement.setAttribute("type", "XML_ELEMENT");
					node = document.appendChild(newElement);
				} else {
					Node n = (Node) xpath.evaluate((schemaType == SchemaType.DATASINK ? "input" : "output") + "[@step='" + reader.getName() + "']", node, XPathConstants.NODE);
					if (n != null) {
						node = n;
					} else {
						Element newElement = document.createElement(schemaType == SchemaType.DATASINK ? "input" : "output");
						newElement.setAttribute("name", reader.getLocalName());
						newElement.setAttribute("step", "" + reader.getName());
						newElement.setAttribute("type", "XML_ELEMENT");
						node = node.appendChild(newElement);
					}
				}
				for (int i = 0; i < reader.getAttributeCount(); i++) {
					Node n = (Node) xpath.evaluate((schemaType == SchemaType.DATASINK ? "input" : "output") + "[@step='@" + reader.getAttributeName(i) + "']", node, XPathConstants.NODE);
					if (n == null) {
						Element newElement = document.createElement(schemaType == SchemaType.DATASINK ? "input" : "output");
						newElement.setAttribute("name", "@" + reader.getAttributeLocalName(i));
						newElement.setAttribute("step", "@" + reader.getAttributeName(i));
						newElement.setAttribute("type", "XML_ATTRIBUTE");
						node.appendChild(newElement);
					}
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				node = node.getParentNode();
				break;
			}
		}
		reader.close();
		
		StringWriter writer = new StringWriter();
		
		String stylesheet = 
				"<xsl:stylesheet version='2.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>" +
				" <xsl:output method='xml' indent='yes'/>" +
				" <xsl:template match='@*|node()'>" +
				"  <xsl:copy>" +
				"   <xsl:apply-templates select='@*|node()'/>" +
				"  </xsl:copy>" +
				" </xsl:template>" +
				"</xsl:stylesheet>";
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(new StreamSource(new StringReader(stylesheet)));
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(new DOMSource(document), new StreamResult(writer));
		
		return writer.toString();
	}
}
