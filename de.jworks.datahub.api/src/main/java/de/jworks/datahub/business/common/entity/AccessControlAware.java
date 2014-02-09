package de.jworks.datahub.business.common.entity;

public interface AccessControlAware {

	public AccessControlList getAcl();
	
	public void setAcl(AccessControlList acl);
	
}
