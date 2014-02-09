package de.jworks.datahub.business.search.boundary;

import javax.enterprise.inject.Produces;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServerProvider {
	
	private static Logger LOGGER = LoggerFactory.getLogger(SolrServerProvider.class);

	@Produces
	public SolrServer getSolrServer() {
		try {
			return new HttpSolrServer("http://localhost:8983/solr");
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}
	
}
