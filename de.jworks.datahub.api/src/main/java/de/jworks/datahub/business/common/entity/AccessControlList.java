package de.jworks.datahub.business.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class AccessControlList {

	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccessControlEntry> entries = new ArrayList<AccessControlEntry>();

	public List<AccessControlEntry> getEntries() {
		return entries;
	}
	
}
