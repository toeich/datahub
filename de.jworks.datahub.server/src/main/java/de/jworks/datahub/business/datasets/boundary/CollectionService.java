package de.jworks.datahub.business.datasets.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.datasets.entity.DatasetGroup;

@Stateless
public class CollectionService {
	
	@Inject
	EntityManager entityManager;

	public DatasetGroup getCollection(long collectionId) {
		DatasetGroup collection = entityManager.find(DatasetGroup.class, collectionId);
		return collection;
	}

	public void updateCollection(DatasetGroup collection) {
		entityManager.merge(collection);
	}

	public void removeCollection(DatasetGroup collection) {
		entityManager.remove(entityManager.merge(collection));
	}

}
