package de.jworks.datahub.business.datasets.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.boundary.AccessControlService;
import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.datasets.entity.ColumnDefinition;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;
import de.jworks.datahub.business.util.XMLUtil;

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
				.createQuery("SELECT dg FROM DatasetGroup dg WHERE dg.project IS NULL OR dg.project = :project", DatasetGroup.class)
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
		
		// sync id into the content as attribute "_id"
		dataset.getDocument().getDocumentElement().setAttribute("_id", String.valueOf(dataset.getId()));
		dataset.updateData();
		entityManager.persist(dataset);
		
		updateSearchIndex(dataset);
		
		return dataset;
	}
	
	public Dataset updateDataset(Dataset dataset) {
		dataset.updateData();
		Dataset _dataset = entityManager.merge(dataset);
		updateSearchIndex(_dataset);
		return _dataset;
	}
	
	public void removeDataset(Dataset dataset) {
		entityManager.remove(entityManager.merge(dataset));
	}

	private void updateSearchIndex(Dataset dataset) {
		DatasetGroup datasetGroup = dataset.getGroup();
		for (ColumnDefinition column : datasetGroup.getColumns()) {
			System.out.format("%s : %s\n", column.getName(), XMLUtil.evaluate(column.getFormat(), dataset.getDocument()));
		}
	}
	
}
