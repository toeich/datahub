package de.jworks.datahub.business.documents.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.jworks.datahub.business.documents.entity.DocumentCollection;
import de.jworks.datahub.business.projects.entity.Project;

@Stateless
public class DocumentService {
	
	@Inject
	EntityManager entityManager;
	
	@Inject
	MongoClient mongoClient;
	
	public List<DocumentCollection> getCollections() {
		List<DocumentCollection> result = entityManager
				.createQuery("SELECT dc FROM DocumentCollection dc", DocumentCollection.class)
				.getResultList();
		// TODO filter result
		return result;
	}

	public List<DocumentCollection> getCollections(Project project) {
		List<DocumentCollection> result = entityManager
				.createQuery("SELECT dc FROM DocumentCollection dc WHERE dc.project IS NULL OR dc.project = :project", DocumentCollection.class)
				.setParameter("project", project)
				.getResultList();
		// TODO filter result
		return result;
	}

	public DocumentCollection getCollection(long collectionId) {
		DocumentCollection collection = entityManager.find(DocumentCollection.class, collectionId);
		return collection;
	}
	
	public DocumentCollection addCollection(DocumentCollection collection) {
		entityManager.persist(collection);
		return collection;
	}
	
	public DocumentCollection updateCollection(DocumentCollection collection) {
		DocumentCollection _collection = getCollection(collection.getId());
		_collection.setName(collection.getName());
		_collection.setDescription(collection.getDescription());
		return _collection;
	}
	
	public void removeCollection(DocumentCollection collection) {
		DocumentCollection _collection = getCollection(collection.getId());
		entityManager.remove(_collection);
	}

	public List<DBObject> getDocuments(DocumentCollection collection) {
		try {
			DB db = mongoClient.getDB("connector");
			DBCollection dbCollection = db.getCollection(collection.getName());
			DBCursor dbCursor = dbCollection.find();
			return dbCursor.toArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addDocument(DBObject document) {
		try {
			DB db = mongoClient.getDB("connector");
			DBCollection dbCollection = db.getCollection((String) document.get("_collection"));
			dbCollection.save(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateDocument(DBObject document) {
		try {
			DB db = mongoClient.getDB("connector");
			DBCollection dbCollection = db.getCollection((String) document.get("_collection"));
			dbCollection.save(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void removeDocument(DBObject document) {
		try {
			DB db = mongoClient.getDB("connector");
			DBCollection dbCollection = db.getCollection((String) document.get("_collection"));
			dbCollection.remove(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
