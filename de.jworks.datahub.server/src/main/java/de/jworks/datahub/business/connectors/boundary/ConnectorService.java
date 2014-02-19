package de.jworks.datahub.business.connectors.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.connectors.entity.Connector;

@Stateless
public class ConnectorService {
	
	@Inject
	EntityManager entityManager;

	public List<Connector> getConnectors() {
		return entityManager
				.createQuery("SELECT c FROM Connector c", Connector.class)
				.getResultList();
	}
	
	public Connector getConnector(long systemId) {
		return entityManager.find(Connector.class, systemId);
	}
	
	public Connector addConnector(Connector connector) {
		entityManager.persist(connector);
		return connector;
	}
	
	public Connector updateConnector(Connector connector) {
		connector.updateData();
		return entityManager.merge(connector);
	}
	
	public void removeConnector(Connector connector) {
		entityManager.remove(entityManager.merge(connector));
	}
	
}
