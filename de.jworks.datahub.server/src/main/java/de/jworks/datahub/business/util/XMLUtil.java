package de.jworks.datahub.business.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtil {

	public static Document parse(String xml) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(xml)));
			return document;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Document document) {
		try {
			StringWriter stringWriter = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
			return stringWriter.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static NodeList selectNodes(String expression, Node node) {
		try {
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			return (NodeList) xpath.evaluate(expression, node, XPathConstants.NODESET);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Node selectNode(String expression, Node node) {
		try {
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			return (Node) xpath.evaluate(expression, node, XPathConstants.NODE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String evaluate(String expression, Document document) {
		try {
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			return xpath.evaluate(expression, document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
