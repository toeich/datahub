package de.jworks.datahub.presentation.data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

import de.jworks.datahub.business.datasets.entity.ColumnDefinition;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.util.XMLUtil;

@SuppressWarnings("rawtypes")
public class DatasetContainer implements Container {
	
	private List<Dataset> datasets;
	
	private LinkedHashMap<String, String> properties;

	public DatasetContainer(List<Dataset> datasets, List<ColumnDefinition> columns) {
		this.datasets = datasets;
		
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
		return datasets.contains(itemId);
	}
	
	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		Dataset dataset = (Dataset) itemId;
		String format = properties.get(propertyId);
		String value = XMLUtil.evaluate(format, XMLUtil.parse(dataset.getContent()));
		return new ObjectProperty<String>(value, String.class, true);
	}
	
	@Override
	public Collection<?> getContainerPropertyIds() {
		return properties.keySet();
	}

	@Override
	public Item getItem(Object itemId) {
		return new DatasetItem((Dataset) itemId, properties);
	}

	@Override
	public Collection<?> getItemIds() {
		return datasets;
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
		return datasets.size();
	}

}
