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

import de.jworks.datahub.business.documents.entity.DatasetGroup;
import de.jworks.datahub.business.projects.entity.Project;

@Stateless
public class DocumentService {
	
	@Inject
	EntityManager entityManager;
	
	@Inject
	MongoClient mongoClient;
	
	public List<DatasetGroup> getCollections() {
		List<DatasetGroup> result = entityManager
				.createQuery("SELECT dc FROM DocumentCollection dc", DatasetGroup.class)
				.getResultList();
		// TODO filter result
		return result;
	}

	public List<DatasetGroup> getCollections(Project project) {
		List<DatasetGroup> result = entityManager
				.createQuery("SELECT dc FROM DocumentCollection dc WHERE dc.project IS NULL OR dc.project = :project", DatasetGroup.class)
				.setParameter("project", project)
				.getResultList();
		// TODO filter result
		return result;
	}

	public DatasetGroup getCollection(long collectionId) {
		DatasetGroup collection = entityManager.find(DatasetGroup.class, collectionId);
		return collection;
	}
	
	public DatasetGroup addCollection(DatasetGroup collection) {
		entityManager.persist(collection);
		return collection;
	}
	
	public DatasetGroup updateCollection(DatasetGroup collection) {
		DatasetGroup _collection = getCollection(collection.getId());
		_collection.setName(collection.getName());
		_collection.setDescription(collection.getDescription());
		return _collection;
	}
	
	public void removeCollection(DatasetGroup collection) {
		DatasetGroup _collection = getCollection(collection.getId());
		entityManager.remove(_collection);
	}

	public List<DBObject> getDocuments(DatasetGroup collection) {
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
