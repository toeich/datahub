package de.jworks.datahub.business.common.boundary;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;

import de.jworks.datahub.business.common.entity.AccessControlAware;
import de.jworks.datahub.business.common.entity.AccessControlEntry;
import de.jworks.datahub.business.common.entity.AccessControlList;
import de.jworks.datahub.business.common.entity.User;

public class AccessControlService {
	
	@Inject
	User user;

	public void filter(Collection<? extends AccessControlAware> accessControlAwares, String permission) {
		Iterator<? extends AccessControlAware> iterator = accessControlAwares.iterator();
		while (iterator.hasNext()) {
			if (!hasPermission(iterator.next(), permission)) {
				iterator.remove();
			}
		}
	}
	
	public boolean hasPermission(AccessControlAware accessControlAware, String permission) {
		AccessControlList acl = accessControlAware.getAcl();
		if (acl == null) {
			return true;
		}
		List<AccessControlEntry> entries = acl.getEntries();
		if (entries.size() > 0)  {
			for (AccessControlEntry entry : entries) {
				if (ObjectUtils.equals(user, entry.getPrincipal()) && ObjectUtils.equals(permission, entry.getPermission())) {
					return true;
				}
			}
		}
		return false;
	}
	
}
