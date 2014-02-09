package de.jworks.datahub.business.common.boundary;

import java.security.Principal;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.common.entity.User;

@Stateless
@RolesAllowed(Role.ADMIN)
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

	public User getUser(String name) {
		return entityManager
				.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
				.setParameter("name", name)
				.getSingleResult();
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

}
