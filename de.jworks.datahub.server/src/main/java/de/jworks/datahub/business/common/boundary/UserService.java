package de.jworks.datahub.business.common.boundary;

import java.security.Principal;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.entity.User;
import de.jworks.datahub.business.common.entity.UserGroup;

@Stateless
//@RolesAllowed(Role.ADMIN)
public class UserService {

	@Inject
	EntityManager entityManager;
	
	@Inject
	Principal principal;
	
	@Produces
	public User getCurrentUser() {
		return principal != null ? getUser(principal.getName()) : null;
	}

	public List<User> getUsers() {
		return entityManager
				.createQuery("SELECT u FROM User u", User.class)
				.getResultList();
	}

	public User getUser(long userId) {
		return entityManager
				.find(User.class, userId);
	}
	
	public User getUser(String name) {
		try {
			return entityManager
					.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
					.setParameter("name", name)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public void addUser(User user) {
		entityManager.persist(user);
	}
	
	public void updateUser(User user) {
		entityManager.merge(user);
	}
	
	public void removeUser(User user) {
		entityManager.remove(entityManager.merge(user));
	}

	public List<UserGroup> getUserGroups() {
		return entityManager
				.createQuery("SELECT ug FROM UserGroup ug", UserGroup.class)
				.getResultList();
	}
	
	public UserGroup getUserGroup(long userGroupId) {
		return entityManager
				.find(UserGroup.class, userGroupId);
	}
	
	public UserGroup addUserGroup(UserGroup userGroup) {
		entityManager.persist(userGroup);
		return userGroup;
	}
	
	public UserGroup updateUserGroup(UserGroup userGroup) {
		UserGroup _userGroup = entityManager.merge(userGroup);
		return _userGroup;
	}
	
}
