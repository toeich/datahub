package de.jworks.datahub.business.common.boundary;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.common.entity.UserGroup;

@Stateless
//@RolesAllowed({ Role.ADMIN })
public class UserGroupService {

	@Inject
	EntityManager entityManager;

	public List<UserGroup> getGroups() {
		return entityManager.createQuery("SELECT ug FROM UserGroup ug", UserGroup.class).getResultList();
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
