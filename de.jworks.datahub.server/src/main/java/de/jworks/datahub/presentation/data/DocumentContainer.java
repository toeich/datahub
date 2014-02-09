package de.jworks.datahub.presentation.data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.mongodb.DBObject;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

import de.jworks.datahub.business.documents.entity.ColumnDefinition;
import de.jworks.datahub.business.util.DBObjectUtil;

@SuppressWarnings("rawtypes")
public class DocumentContainer implements Container {
	
	private List<DBObject> dbObjects;
	
	private LinkedHashMap<String, String> properties;

	public DocumentContainer(List<DBObject> dbObjects, List<ColumnDefinition> columns) {
		this.dbObjects = dbObjects;
		
		properties = new LinkedHashMap<String, String>();
		for (ColumnDefinition column : columns) {
			properties.put(column.getName(), column.getFormat());
		}
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
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
	public boolean containsId(Object itemId) {
		return dbObjects.contains(itemId);
	}
	
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		DBObject dbObject = (DBObject) itemId;
		String format = properties.get(propertyId);
		String value = DBObjectUtil.format(dbObject, format);
		return new ObjectProperty<String>(value, String.class, true);
	}
	
	@Override
	public Collection<?> getContainerPropertyIds() {
		return properties.keySet();
	}

	@Override
	public Item getItem(Object itemId) {
		return new DocumentItem((DBObject) itemId, properties);
	}

	@Override
	public Collection<?> getItemIds() {
		return dbObjects;
	}
	
	@Override
	public Class<?> getType(Object propertyId) {
		return Object.class;
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int size() {
		return dbObjects.size();
	}

}
