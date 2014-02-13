package de.jworks.datahub.business.search.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

import de.jworks.datahub.business.datasets.entity.Dataset;

@Stateless
public class SearchService {
	
	@Inject
	SolrServer solrServer;

	public String search(String query) {
		try {
			SolrQuery solrQuery = new SolrQuery();
			for (String param : query.split("&")) {
				String name = param.split("=", 2)[0];
				String value = param.split("=", 2)[1];
				solrQuery.setParam(name, value);
			}
			QueryResponse response = solrServer.query(solrQuery);
			StringBuffer buffer = new StringBuffer();
			for (SolrDocument document : response.getResults()) {
				buffer.append(document.toString());
			}
			return buffer.toString();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void addDocument(Dataset document) {
		
	}
	
	public void updateDocument(Dataset document) {
		
	}
	
	public void removeDocument(Dataset document) {
		
	}
	
}
