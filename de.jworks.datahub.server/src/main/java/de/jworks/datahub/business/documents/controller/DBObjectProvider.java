package de.jworks.datahub.business.documents.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Provider
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class DBObjectProvider implements MessageBodyReader<DBObject>, MessageBodyWriter<DBObject> {
	
//	@Inject
//	Providers providers;
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}
	
	@Override
	public DBObject readFrom(Class<DBObject> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(entityStream);
			DBObject dbObject = process(document);
			return dbObject;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private DBObject process(Document document) throws Exception {
		Element element = document.getDocumentElement();
		
		DBObject dbObject = new BasicDBObject();
		dbObject.put("_id", element.getAttribute("_id"));
		dbObject.put("_name", element.getNodeName());
		dbObject.put("_collection", element.getAttribute("_collection"));
		
		process(element, dbObject);
		
		return dbObject;
	}

	private DBObject process(Element element, DBObject dbObject) throws Exception {
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node node = attributes.item(i);
			dbObject.put(node.getNodeName(), node.getNodeValue());
		}
		
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node instanceof Element) {
				@SuppressWarnings("unchecked")
				List<DBObject> list = (List<DBObject>) dbObject.get(node.getNodeName());
				if (list == null) {
					dbObject.put(node.getNodeName(), list = new ArrayList<DBObject>());
				}
				DBObject dbObject2 = new BasicDBObject();
				list.add(dbObject2);
				process((Element) node, dbObject2);
			}
		}
		
		return dbObject;
	}
	
	@Override
	public long getSize(DBObject dbObject, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public void writeTo(DBObject dbObject, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			Element element = document.createElement(String.valueOf(dbObject.get("_name")));
			element.setAttribute("_id", String.valueOf(dbObject.get("_id")));
			element.setAttribute("_collection", String.valueOf(dbObject.get("_collection")));
			document.appendChild(element);

			process(dbObject, element);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(entityStream));
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private void process(DBObject dbObject, Element element) {
		for (String key : dbObject.keySet()) {
			if (key.startsWith("_")) continue;
			Object value = dbObject.get(key);
			if (value instanceof Collection<?>) {
				for (Object object : (Collection<?>) value) {
					if (object instanceof DBObject) {
						Document document = element.getOwnerDocument();
						Element newElement = document.createElement(key);
						element.appendChild(newElement);
						process((DBObject) object, newElement);
					}
				}
			} else {
				element.setAttribute(key, String.valueOf(value));
			}
		}
	}
	
}
