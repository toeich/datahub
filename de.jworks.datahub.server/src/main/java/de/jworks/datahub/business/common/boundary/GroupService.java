package de.jworks.datahub.business.common.boundary;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.business.common.entity.Role;

@Stateless
@RolesAllowed({ Role.ADMIN })
public class GroupService {

	@Inject
	EntityManager entityManager;

	public List<UserGroup> getGroups() {
		return entityManager.createQuery("SELECT g FROM Group g", UserGroup.class).getResultList();
	}

	public void addGroup(UserGroup group) {
		entityManager.persist(group);
	}

	public void updateGroup(UserGroup group) {
		entityManager.merge(group);
	}

	public void removeGroup(UserGroup group) {
		entityManager.remove(entityManager.merge(group));
	}

}
