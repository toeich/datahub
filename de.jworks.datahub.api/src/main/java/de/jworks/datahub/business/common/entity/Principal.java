package de.jworks.datahub.business.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class Principal {

	@Id
	@GeneratedValue
	protected Long id;
	
	protected String name;
	
	@ElementCollection(fetch = FetchType.EAGER)
	protected Set<String> roles = new HashSet<String>();
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
}
