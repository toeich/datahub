package de.jworks.datahub.presentation.data;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.mongodb.DBObject;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

import de.jworks.datahub.business.util.DBObjectUtil;

@SuppressWarnings("rawtypes")
public class DocumentItem implements Item {
	
	private DBObject dbObject;
	
	private LinkedHashMap<String, String> properties;

	public DocumentItem(DBObject dbObject, LinkedHashMap<String, String> properties) {
		this.dbObject = dbObject;
		this.properties = properties;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return properties.keySet();
	}
	
	@Override
	public boolean addItemProperty(Object id, Property property) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeItemProperty(Object id) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Property getItemProperty(Object id) {
		return new ObjectProperty<Object>(DBObjectUtil.format(dbObject, properties.get(id)), Object.class, true);
	}
	
}
