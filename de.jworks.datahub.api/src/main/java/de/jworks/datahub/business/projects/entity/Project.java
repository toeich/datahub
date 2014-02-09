package de.jworks.datahub.business.projects.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;

import de.jworks.datahub.business.common.entity.AccessControlAware;
import de.jworks.datahub.business.common.entity.AccessControlList;

@Entity
public class Project implements AccessControlAware {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "locale")
	@Column(name = "name")
	private Map<String, String> localizedNames = new HashMap<String, String>();
	
	@ElementCollection
	@MapKeyColumn(name = "locale")
	@Column(name = "description")
	private Map<String, String> localizedDescriptions = new HashMap<String, String>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private AccessControlList acl;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Map<String, String> getLocalizedNames() {
		return localizedNames;
	}
	
	public Map<String, String> getLocalizedDescriptions() {
		return localizedDescriptions;
	}

	@Override
	public AccessControlList getAcl() {
		return acl;
	}

	@Override
	public void setAcl(AccessControlList acl) {
		this.acl = acl;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
