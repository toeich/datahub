package de.jworks.datahub.presentation.data;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

import de.jworks.datahub.business.documents.entity.Dataset;
import de.jworks.datahub.business.util.XMLUtil;

@SuppressWarnings("rawtypes")
public class DatasetItem implements Item {
	
	private Dataset dataset;
	
	private LinkedHashMap<String, String> properties;

	public DatasetItem(Dataset dataset, LinkedHashMap<String, String> properties) {
		this.dataset = dataset;
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
		return new ObjectProperty<Object>(XMLUtil.evaluate(properties.get(id), XMLUtil.parse(dataset.getContent())), Object.class, true);
	}
	
}
