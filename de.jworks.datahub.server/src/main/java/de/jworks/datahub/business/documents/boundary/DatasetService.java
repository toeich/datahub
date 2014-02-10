package de.jworks.datahub.business.documents.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.boundary.AccessControlService;
import de.jworks.datahub.business.documents.entity.Dataset;
import de.jworks.datahub.business.documents.entity.DatasetGroup;
import de.jworks.datahub.business.projects.entity.Project;

@Stateless
public class DatasetService {
	
	@Inject
	EntityManager entityManager;
	
	@Inject
	AccessControlService accessControlService;
	
	public List<DatasetGroup> getDatasetGroups() {
		List<DatasetGroup> result = entityManager
				.createQuery("SELECT dg FROM DatasetGroup dg", DatasetGroup.class)
				.getResultList();
//		accessControlService.filter(result, Permission.READ);
		return result;
	}

	public List<DatasetGroup> getDatasetGroups(Project project) {
		List<DatasetGroup> result = entityManager
				.createQuery("SELECT dg FROM DatasetGroup dg WHERE dg.project IS NULL OR dg.project = :project", DatasetGroup.class)
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

	public List<Dataset> getDatasets(DatasetGroup datasetGroup) {
		try {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addDataset(Dataset dataset) {
		try {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void updateDataset(Dataset dataset) {
		try {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void removeDataset(Dataset dataset) {
		try {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
