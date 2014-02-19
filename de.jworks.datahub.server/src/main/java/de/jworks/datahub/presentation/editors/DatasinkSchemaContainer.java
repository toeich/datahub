package de.jworks.datahub.presentation.editors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

import de.jworks.datahub.business.transform.entity.ComponentSchema;
import de.jworks.datahub.business.transform.entity.Input;

public class DatasinkSchemaContainer implements Container.Hierarchical {
	
	private ComponentSchema schema;
	
	private final Map<Input, Input> parents = new HashMap<Input, Input>();

	public DatasinkSchemaContainer(ComponentSchema schema) {
		this.schema = schema;
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
		return true;
	}

	@Override
	public boolean containsId(Object itemId) {
		return true;
	}

	@Override
	public java.util.Collection<?> getChildren(Object itemId) {
		Input parent = (Input) itemId;
		List<Input> children = parent.getInputs();
		for (Input child : children) {
			parents.put(child, parent);
		}
		return children;
	}

	@Override
	public Property<?> getContainerProperty(Object itemId, Object propertyId) {
		if ("name".equals(propertyId)) {
			return new MethodProperty<String>(itemId, "name");
		}
		return null;
	}

	@Override
	public java.util.Collection<?> getContainerPropertyIds() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Item getItem(Object itemId) {
		return null;
	}

	@Override
	public java.util.Collection<?> getItemIds() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object getParent(Object itemId) {
		return parents.get(itemId);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public boolean hasChildren(Object itemId) {
		return ((Input) itemId).getInputs().size() > 0;
	}

	@Override
	public boolean isRoot(Object itemId) {
		throw new UnsupportedOperationException("not implemented");
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
	public java.util.Collection<?> rootItemIds() {
		return schema.getInputs();
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