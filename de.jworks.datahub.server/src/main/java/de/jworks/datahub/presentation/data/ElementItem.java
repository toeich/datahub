package de.jworks.datahub.presentation.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

@SuppressWarnings("rawtypes")
public class ElementItem implements Item {
	
	protected List<Object> itemPropertyIds = new ArrayList<Object>();
	
	protected Element element;
	
	public ElementItem(Element element) {
		this.element = element;
		
		NamedNodeMap attributes = element.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node item = attributes.item(i);
			itemPropertyIds.add(item.getNodeName());
		}
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return Collections.unmodifiableCollection(itemPropertyIds);
	}
	
	@Override
	public Property getItemProperty(Object id) {
		try {
			Method getMethod = Element.class.getMethod("getAttribute", String.class);
			Method setMethod = Element.class.getMethod("setAttribute", String.class, String.class);
			return new MethodProperty<String>(String.class, element, getMethod, setMethod, new Object[] { id }, new Object[] { id, null }, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
}