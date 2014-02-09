package de.jworks.datahub.business.documents.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.documents.entity.DocumentCollection;

@Stateless
public class CollectionService {
	
	@Inject
	EntityManager entityManager;

	public DocumentCollection getCollection(long collectionId) {
		DocumentCollection collection = entityManager.find(DocumentCollection.class, collectionId);
		return collection;
	}

	public void updateCollection(DocumentCollection collection) {
		entityManager.merge(collection);
	}

	public void removeCollection(DocumentCollection collection) {
		entityManager.remove(entityManager.merge(collection));
	}

}
