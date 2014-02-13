package de.jworks.datahub.business.search.boundary;

import java.util.ArrayList;
import java.util.List;

import de.jworks.datahub.business.datasets.entity.Dataset;

public class SearchResult extends ArrayList<Dataset> {

	private List<Dataset> datasets;
	
	public List<Dataset> getDocuments() {
		return datasets;
	}
	
	public void setDocuments(List<Dataset> datasets) {
		this.datasets = datasets;
	}
	
}
