package de.jworks.datahub.business.datasets.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.boundary.AccessControlService;
import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;

@Stateless
public class DatasetService {
	
	@Inject
	EntityManager entityManager;
	
	@Inject
	AccessControlService accessControlService;
	
	// ===== DATASET GROUP =====
	
	public List<DatasetGroup> getDatasetGroups() {
		return entityManager
				.createQuery("SELECT dg FROM DatasetGroup dg", DatasetGroup.class)
				.getResultList();
	}

	public List<DatasetGroup> getDatasetGroups(Project project) {
		return entityManager
				.createQuery("SELECT dg FROM DatasetGroup dg WHERE dg.projects IS EMPTY OR :project MEMBER OF dg.projects", DatasetGroup.class)
				.setParameter("project", project)
				.getResultList();
	}

	public DatasetGroup getDatasetGroup(long datasetGroupId) {
		return entityManager
				.find(DatasetGroup.class, datasetGroupId);
	}
	
	public DatasetGroup addDatasetGroup(DatasetGroup datasetGroup) {
		entityManager.persist(datasetGroup);
		return datasetGroup;
	}
	
	public DatasetGroup updateDatasetGroup(DatasetGroup datasetGroup) {
		datasetGroup.updateData();
		return entityManager.merge(datasetGroup);
	}
	
	public void removeDatasetGroup(DatasetGroup datasetGroup) {
		entityManager.remove(entityManager.merge(datasetGroup));
	}

	// ===== DATASET =====
	
	public List<Dataset> getDatasets(DatasetGroup datasetGroup) {
		return entityManager
				.createQuery("SELECT d FROM Dataset d WHERE d.group = :group", Dataset.class)
				.setParameter("group", datasetGroup)
				.getResultList();
	}
	
	public Dataset getDataset(long datasetId) {
		return entityManager
				.find(Dataset.class, datasetId);
	}

	public Dataset addDataset(Dataset dataset) {
		entityManager.persist(dataset);
		return dataset;
	}
	
	public Dataset updateDataset(Dataset dataset) {
		dataset.updateData();
		return entityManager.merge(dataset);
	}
	
	public void removeDataset(Dataset dataset) {
		entityManager.remove(entityManager.merge(dataset));
	}

}
