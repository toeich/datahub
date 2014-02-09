package de.jworks.datahub.business.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DBObjectUtil {

	static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	public static DBObject parse(String xml) {
		try {
			return toDBObject(XMLUtil.parse(xml));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String toString(DBObject dbObject) {
		try {
			return XMLUtil.toString(toDocument(dbObject));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static DBObject toDBObject(Document document) {
		try {
			Element element = document.getDocumentElement();

			DBObject dbObject = new BasicDBObject();
			
			if (!"".equals(element.getAttribute("_id"))) {
				dbObject.put("_id", element.getAttribute("_id"));
			}
			
			dbObject.put("_name", element.getNodeName());
			dbObject.put("_collection", element.getAttribute("_collection"));

			NodeList childNodes = element.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node node = childNodes.item(i);
				if (node instanceof Element) {
					process((Element) node, dbObject);
				}
			}

			return dbObject;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static DBObject process(Element element, DBObject dbObject) throws Exception {
		List<DBObject> list = null;

		boolean processed = false;
		
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if (node instanceof Element) {
				if (list == null) {
					list = (List<DBObject>) dbObject.get(element.getNodeName());
					if (list == null) {
						dbObject.put(element.getNodeName(), list = new ArrayList<DBObject>());
					}
				}
				DBObject dbObject2 = new BasicDBObject();
				list.add(dbObject2);
				process((Element) node, dbObject2);
				processed = true;
			}
		}
		
		if (!processed) {
			dbObject.put(element.getNodeName(), element.getTextContent());
		}

		return dbObject;
	}

	public static Document toDocument(DBObject dbObject) {
		try {
			Document document = documentBuilderFactory.newDocumentBuilder().newDocument();

			Element element = document.createElement(String.valueOf(dbObject.get("_name")));
			element.setAttribute("_id", String.valueOf(dbObject.get("_id")));
			element.setAttribute("_collection", String.valueOf(dbObject.get("_collection")));
			document.appendChild(element);

			process(dbObject, element);

			return document;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void process(DBObject dbObject, Node node) {
		for (String key : dbObject.keySet()) {
			if (key.startsWith("_")) continue;
			Object value = dbObject.get(key);
			if (value instanceof Collection<?>) {
				for (Object object : (Collection<?>) value) {
					if (object instanceof DBObject) {
						Element element = node.getOwnerDocument().createElement(key);
						node.appendChild(element);
						process((DBObject) object, element);
					}
				}
			} else if (value instanceof DBObject) {
				Element element = node.getOwnerDocument().createElement(key);
				node.appendChild(element);
				process((DBObject) value, element);
			} else {
				Element element = node.getOwnerDocument().createElement(key);
				node.appendChild(element);
				element.setTextContent(String.valueOf(value));
			}
		}
	}

	public static String format(DBObject object, String format) {
		StringBuilder builder = new StringBuilder(format.length());
		for (int i = 0; i < format.length(); i++) {
			char c = format.charAt(i);
			if (c == '{') {
				int index = format.indexOf('}', i);
				builder.append(get(object, format.substring(i + 1, index)));
				i = index;
			} else {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(DBObject object, String path) {
		try {
			String[] steps = path.split("\\.");
			for (int i = 0; i < steps.length - 1; i++) {
				object = (DBObject) object.get(steps[i]);
			}
			return (T) object.get(steps[steps.length - 1]);
		} catch (Exception e) {
			return null;
		}
	}

	public static void put(DBObject object, String path, Object value) {
		try {
			String[] steps = path.split("\\.");
			for (int i = 0; i < steps.length - 1; i++) {
				object = (DBObject) object.get(steps[i]);
			}
			object.put(steps[steps.length - 1], value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
