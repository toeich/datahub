package de.jworks.datahub.business.search.boundary;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

public class SearchResult extends ArrayList<DBObject> {

	private List<DBObject> documents;
	
	public List<DBObject> getDocuments() {
		return documents;
	}
	
	public void setDocuments(List<DBObject> documents) {
		this.documents = documents;
	}
	
}
