package de.jworks.datahub.presentation.data;

import java.util.Arrays;
import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

import de.jworks.datahub.business.transform.entity.Output;

@SuppressWarnings("rawtypes")
public class OutputItem implements Item {

	public static final String NAME = "name";
	
	public static final String STEP = "step";
	
	private Output output;
	
	public OutputItem(Output output) {
		this.output = output;
	}

	@Override
	public Property getItemProperty(Object id) {
		if (NAME.equals(id)) {
			return new MethodProperty<String>(output, "name");
		}
		if (STEP.equals(id)) {
			return new MethodProperty<String>(output, "step");
		}
		return null;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return Arrays.asList(NAME, STEP);
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
