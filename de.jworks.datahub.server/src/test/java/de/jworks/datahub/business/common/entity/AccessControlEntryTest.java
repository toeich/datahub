package de.jworks.datahub.business.common.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.jworks.datahub.business.common.entity.AccessControlEntry;
import de.jworks.datahub.business.common.entity.User;

public class AccessControlEntryTest {

	private AccessControlEntry cut;
	
	@Before
	public void setUp() throws Exception {
		cut = new AccessControlEntry();
	}

	@Test
	public void testPrincipal() {
		assertNull(cut.getPrincipal());
		
		User user = new User();
		cut.setPrincipal(user);
		assertEquals(user, cut.getPrincipal());
	}

	@Test
	public void testPermission() {
		assertNull(cut.getPermission());
		
		cut.setPermission("foo");
		assertEquals("foo", cut.getPermission());
	}

}
