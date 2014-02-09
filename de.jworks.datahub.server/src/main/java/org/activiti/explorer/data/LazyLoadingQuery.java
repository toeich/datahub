package org.activiti.explorer.data;

import java.io.Serializable;
import java.util.List;

import com.vaadin.data.Item;

public interface LazyLoadingQuery extends Serializable {

	public int size();

	public List<Item> loadItems(int start, int count);

	public Item loadSingleResult(String id);

	public void setLazyLoadingContainer(LazyLoadingContainer lazyLoadingContainer);

	public void setSorting(Object[] propertyIds, boolean[] ascending);

}
