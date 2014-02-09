package de.jworks.datahub.presentation.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

import de.jworks.datahub.business.transform.entity.Output;

@SuppressWarnings("rawtypes")
public class OutputContainer implements Container, Container.Hierarchical {
	
	public static final String NAME = "name";
	
	private List<Output> outputs;

	public OutputContainer(List<Output> outputs) {
		this.outputs = outputs;
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
	public boolean areChildrenAllowed(Object itemId) {
		if (itemId instanceof Output) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsId(Object itemId) {
		if (itemId instanceof Output) {
			Output output = (Output) itemId;
			while (output != null) {
				if (outputs.contains(output)) {
					return true;
				}
				output = output.getParent();
			}
		}		
		return false;
	}

	@Override
	public Collection<?> getChildren(Object itemId) {
		if (itemId instanceof Output) {
			Output output = (Output) itemId;
			return output.getOutputs();
		}
		return null;
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if (itemId instanceof Output) {
			if (NAME.equals(propertyId)) {
				return new MethodProperty<String>(itemId, "name");
			}
		}
		return null;
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return Arrays.asList(NAME);
	}

	@Override
	public Item getItem(Object itemId) {
		if (itemId instanceof Output) {
			Output output = (Output) itemId;
			return new OutputItem(output);
		}
		return null;
	}

	@Override
	public Collection<?> getItemIds() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object getParent(Object itemId) {
		if (itemId instanceof Output) {
			Output output = (Output) itemId;
			return output.getParent();
		}
		return null;
	}

	@Override
	public Class<?> getType(Object propertyId) {
		if (NAME.equals(propertyId)) {
			return String.class;
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object itemId) {
		if (itemId instanceof Output) {
			Output output = (Output) itemId;
			return output.getOutputs().size() > 0;
		}
		return false;
	}

	@Override
	public boolean isRoot(Object itemId) {
		return outputs.contains(itemId);
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
	public Collection<?> rootItemIds() {
		return outputs;
	}

	@Override
	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean setParent(Object itemId, Object newParentId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException("not implemented");
	}

}
