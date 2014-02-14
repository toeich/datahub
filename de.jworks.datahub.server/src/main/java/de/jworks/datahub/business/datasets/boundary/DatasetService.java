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

	public DatasetGroup getDatasetGroup(long datasetGroupId) {
		DatasetGroup result = entityManager.find(DatasetGroup.class, datasetGroupId);
		return result;
	}
	
	public DatasetGroup addDatasetGroup(DatasetGroup datasetGroup) {
		entityManager.persist(datasetGroup);
		return datasetGroup;
	}
	
	public DatasetGroup updateDatasetGroup(DatasetGroup datasetGroup) {
		DatasetGroup _datasetGroup = getDatasetGroup(datasetGroup.getId());
		_datasetGroup.setName(datasetGroup.getName());
		_datasetGroup.setDescription(datasetGroup.getDescription());
		return _datasetGroup;
	}
	
	public void removeDatasetGroup(DatasetGroup datasetGroup) {
		DatasetGroup _datasetGroup = getDatasetGroup(datasetGroup.getId());
		entityManager.remove(_datasetGroup);
	}

	// ===== DATASET =====
	
	public List<Dataset> getDatasets(DatasetGroup datasetGroup) {
		List<Dataset> result = entityManager
				.createQuery("SELECT d FROM Dataset d WHERE d.group = :group", Dataset.class)
				.setParameter("group", datasetGroup)
				.getResultList();
		return result;
	}
	
	public Dataset getDataset(long datasetId) {
		Dataset result = entityManager.find(Dataset.class, datasetId);
		return result;
	}

	public Dataset addDataset(Dataset dataset) {
		entityManager.persist(dataset);
		return dataset;
	}
	
	public Dataset updateDataset(Dataset dataset) {
		Dataset _dataset = getDataset(dataset.getId());
		_dataset.setContent(dataset.getContent());
		return _dataset;
	}
	
	public void removeDataset(Dataset dataset) {
		Dataset _dataset = getDataset(dataset.getId());
		entityManager.remove(_dataset);
	}

}
