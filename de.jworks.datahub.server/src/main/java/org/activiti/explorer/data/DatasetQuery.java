package org.activiti.explorer.data;

import java.util.List;

import javax.persistence.EntityManager;

import com.vaadin.data.Item;

import de.jworks.datahub.business.datasets.entity.DatasetGroup;

public class DatasetQuery extends AbstractLazyLoadingQuery {
	
	private final EntityManager entityManager;
	
	private DatasetGroup datasetGroup;

	public DatasetQuery(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void setDatasetGroup(DatasetGroup datasetGroup) {
		this.datasetGroup = datasetGroup;
	}
	
	@Override
	public int size() {
		return entityManager
				.createQuery("SELECT COUNT(d) FROM Dataset d WHERE d.group = :group", Integer.class)
				.setParameter("group", datasetGroup)
				.getSingleResult();
	}

	@Override
	public List<Item> loadItems(int start, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item loadSingleResult(Object itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSorting(Object[] propertyIds, boolean[] ascending) {
		// TODO Auto-generated method stub
		
	}

}
