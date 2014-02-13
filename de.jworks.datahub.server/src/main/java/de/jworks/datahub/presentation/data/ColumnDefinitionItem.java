package de.jworks.datahub.presentation.data;

import java.util.Arrays;
import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

import de.jworks.datahub.business.datasets.entity.ColumnDefinition;

@SuppressWarnings("rawtypes")
public class ColumnDefinitionItem implements Item {
	
	public static final String NAME = "name";
	
	public static final String FORMAT = "format";

	private ColumnDefinition column;
	
	public ColumnDefinitionItem(ColumnDefinition column) {
		this.column = column;
	}

	@Override
	public Property getItemProperty(Object id) {
		if (NAME.equals(id)) {
			return new MethodProperty<String>(column, "name");
		}
		if (FORMAT.equals(id)) {
			return new MethodProperty<String>(column, "format");
		}
		return null;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return Arrays.asList(NAME, FORMAT);
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
