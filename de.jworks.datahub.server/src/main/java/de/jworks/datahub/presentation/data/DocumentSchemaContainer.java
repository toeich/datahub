package de.jworks.datahub.presentation.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;

import de.jworks.datahub.business.documents.entity.Attribute;
import de.jworks.datahub.business.documents.entity.DatasetSchema;
import de.jworks.datahub.business.documents.entity.Element;

public class DocumentSchemaContainer implements Container.Hierarchical {
	
	private DatasetSchema documentSchema;
	
	public DocumentSchemaContainer(DatasetSchema documentSchema) {
		this.documentSchema = documentSchema;
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
		if (itemId instanceof Element) {
			return true;
		}
		return false;
	}

	@Override
	public boolean containsId(Object itemId) {
		if (itemId instanceof Element) {
			return true;
		}
		if (itemId instanceof Attribute) {
			return true;
		}
		return false;
	}

	@Override
	public java.util.Collection<?> getChildren(Object itemId) {
		if (itemId instanceof Element) {
			List<Object> children = new ArrayList<Object>();
			children.addAll(((Element) itemId).getAttributes());
			children.addAll(((Element) itemId).getElements());
			return Collections.unmodifiableCollection(children);
		}
		return null;
	}

	@Override
	public Property<?> getContainerProperty(Object itemId, Object propertyId) {
		if (itemId instanceof Element) {
			if ("name".equals(propertyId)) {
				return new MethodProperty<String>(itemId, "name");
			}
		}
		if (itemId instanceof Attribute) {
			if ("name".equals(propertyId)) {
				return new MethodProperty<String>(itemId, "name");
			}
		}
		return null;
	}

	@Override
	public java.util.Collection<?> getContainerPropertyIds() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Item getItem(Object itemId) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public java.util.Collection<?> getItemIds() {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public Object getParent(Object itemId) {
		if (itemId instanceof Element) {
			return ((Element) itemId).getParent();
		}
		if (itemId instanceof Attribute) {
			return ((de.jworks.datahub.business.documents.entity.Item) itemId).getParent();
		}
		return null;
	}

	@Override
	public Class<?> getType(Object propertyId) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public boolean hasChildren(Object itemId) {
		if (itemId instanceof Element) {
			return ((Element) itemId).getElements().size() > 0 || ((Element) itemId).getAttributes().size() > 0;
		}
		return false;
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
		List<? extends Object> rootItemIds = Arrays.asList(documentSchema.getRootElement());
		return Collections.unmodifiableCollection(rootItemIds);
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