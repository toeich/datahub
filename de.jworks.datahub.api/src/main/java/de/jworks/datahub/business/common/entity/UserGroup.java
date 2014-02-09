package de.jworks.datahub.business.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class UserGroup extends Principal {
	
	@ManyToMany
	protected Set<User> users = new HashSet<User>();

	public Set<User> getUsers() {
		return users;
	}
	
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
