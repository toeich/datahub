package de.jworks.datahub.presentation.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

import de.jworks.datahub.business.datasets.entity.ColumnDefinition;

@SuppressWarnings("rawtypes")
public class ColumnDefinitionContainer implements Container {

	public static final String NAME = "name";
	
	public static final String FORMAT = "format";

	private List<ColumnDefinition> columns;

	public ColumnDefinitionContainer(List<ColumnDefinition> columns) {
		this.columns = columns;
	}

	@Override
	public Item getItem(Object itemId) {
		if (itemId instanceof ColumnDefinition) {
			return new ColumnDefinitionItem((ColumnDefinition) itemId);
		}
		return null;
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return Arrays.asList(NAME, FORMAT);
	}

	@Override
	public Collection<?> getItemIds() {
		return columns;
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if (NAME.equals(propertyId)) {
			return new MethodProperty<String>(itemId, "name");
		}
		if (FORMAT.equals(propertyId)) {
			return new MethodProperty<String>(itemId, "format");
		}
		return null;
	}

	@Override
	public Class<?> getType(Object propertyId) {
		if (NAME.equals(propertyId)) {
			return String.class;
		}
		if (FORMAT.equals(propertyId)) {
			return String.class;
		}
		return null;
	}

	@Override
	public int size() {
		return columns.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return columns.contains(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
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
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	} 
	
}
