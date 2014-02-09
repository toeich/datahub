package de.jworks.datahub.business.common.entity;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import de.jworks.datahub.business.common.entity.UserGroup;
import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.common.entity.User;

public class GroupTest {

	private UserGroup cut;
	
	@Before
	public void setUp() throws Exception {
		cut = new UserGroup();
	}
	
	@Test
	public void testName() {
		assertNull(cut.getName());
		
		cut.setName("foo");
		assertEquals("foo", cut.getName());
	}
	
	@Test
	public void testRoles() {
		assertNotNull(cut.getRoles());
		assertEquals(0, cut.getRoles().size());
		
		cut.getRoles().add(Role.USER);
		assertEquals(1, cut.getRoles().size());
	}

	@Test
	public void testRoles2() {
		cut.setRoles(Collections.singleton(Role.USER));
		assertEquals(1, cut.getRoles().size());
	}

	@Test
	public void testUsers() {
		assertNotNull(cut.getUsers());
		assertEquals(0, cut.getUsers().size());
		
		cut.getUsers().add(new User());
		assertEquals(1, cut.getUsers().size());
	}

	@Test
	public void testUsers2() {
		cut.setUsers(Collections.singleton(new User()));
		assertEquals(1, cut.getUsers().size());
	}

}
