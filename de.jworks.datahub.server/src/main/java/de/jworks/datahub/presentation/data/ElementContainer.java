package de.jworks.datahub.presentation.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

public class ElementContainer implements Container, Container.Hierarchical {
	
	public static final String PROPERTY_NAME = "name";
	
	protected List<Object> containerPropertyIds = new ArrayList<Object>();
	protected Map<Object, Class<?>> containerPropertyTypes = new HashMap<Object, Class<?>>();
	protected Map<Object, Object> containerPropertyDefaultValues = new HashMap<Object, Object>();
	
	protected List<Object> rootItemIds = new ArrayList<Object>();
	
	protected Document document;

	public ElementContainer(Document document) {
		this.document = document;
		
		containerPropertyIds.add(PROPERTY_NAME);
		containerPropertyTypes.put(PROPERTY_NAME, String.class);
		containerPropertyDefaultValues.put(PROPERTY_NAME, null);
		
		rootItemIds.add(document.getDocumentElement());
	}
	
	// ----- Collection -----

	@Override
	public Collection<?> getContainerPropertyIds() {
		return Collections.unmodifiableCollection(containerPropertyIds);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return containerPropertyTypes.get(propertyId);
	}

	public Object getDefaultValue(Object propertyId) {
		return containerPropertyDefaultValues.get(propertyId);
	}

	@Override
	public Property<?> getContainerProperty(Object itemId, Object propertyId) {
		if (itemId instanceof Element) { 
			if (PROPERTY_NAME.equals(propertyId)) {
				return new MethodProperty<String>(String.class, itemId, "getNodeName", null);
			}
		}
		return null;
	}

	@Override
	public boolean containsId(Object itemId) {
		return (itemId instanceof Element);
	}

	@Override
	public Item getItem(Object itemId) {
		return (itemId instanceof Element) ? new ElementItem((Element) itemId) : null;
	}

	@Override
	public int size() {
		throw new RuntimeException("size() not implemented");
	}

	@Override
	public Collection<?> getItemIds() {
		throw new RuntimeException("getItemIds() not implemented");
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	// ----- Collection.Hierarchical -----
	
	@Override
	public Collection<?> rootItemIds() {
		return Collections.unmodifiableCollection(rootItemIds);
	}
	
	@Override
	public boolean isRoot(Object itemId) {
		return rootItemIds.contains(itemId);
	}
	
	@Override
	public Object getParent(Object itemId) {
		return (itemId instanceof Element) ? ((Element) itemId).getParentNode() : null;
	}
	
	@Override
	public boolean areChildrenAllowed(Object itemId) {
		return true;
	}
	
	@Override
	public boolean hasChildren(Object itemId) {
		return getChildren(itemId).size() > 0;
	}
	
	@Override
	public Collection<?> getChildren(Object itemId) {
		List<Element> children = new ArrayList<Element>();
		if (itemId instanceof Element) {
			NodeList childNodes = ((Element) itemId).getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node item = childNodes.item(i);
				if (item instanceof Element) {
					children.add((Element) item);
				}
			}
		}
		return children;
	}
	
	@Override
	public boolean setParent(Object itemId, Object newParentId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
}