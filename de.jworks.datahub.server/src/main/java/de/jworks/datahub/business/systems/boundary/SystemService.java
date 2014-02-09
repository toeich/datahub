package de.jworks.datahub.business.systems.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.systems.entity.System;

@Stateless
public class SystemService {
	
	@Inject
	EntityManager entityManager;

	public List<System> getSystems() {
		List<System> result = entityManager
				.createQuery("SELECT s FROM System s", System.class)
				.getResultList();
		return result;
	}
	
	public System getSystem(long systemId) {
		System system = entityManager.find(System.class, systemId);
		return system;
	}
	
	public void addSystem(System system) {
		entityManager.persist(system);
	}
	
	public void updateSystem(System system) {
		entityManager.merge(system);
	}
	
	public void removeSystem(System system) {
		entityManager.remove(entityManager.merge(system));
	}
	
}
