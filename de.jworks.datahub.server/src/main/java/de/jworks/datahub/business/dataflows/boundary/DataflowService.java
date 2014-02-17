package de.jworks.datahub.business.dataflows.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.dataflows.entity.Query;

@Stateless
public class DataflowService {

	@Inject
	EntityManager entityManager;
	
	public List<Query> getQueries() {
		return entityManager
				.createQuery("SELECT q FROM Query q", Query.class)
				.getResultList();
	}
	
	public Query getQuery(long queryId) {
		return entityManager
				.find(Query.class, queryId);
	}
	
	public Query addQuery(Query query) {
		entityManager.persist(query);
		return query;
	}
	
	public Query updateQuery(Query query) {
		return entityManager.merge(query);
	}
	
	public void removeQuery(Query query) {
		entityManager.remove(entityManager.merge(query));
	}
	
}
