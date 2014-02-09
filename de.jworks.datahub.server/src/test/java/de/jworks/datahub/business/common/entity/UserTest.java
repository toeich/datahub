package de.jworks.datahub.business.common.entity;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.common.entity.User;

public class UserTest {
	
	private User cut;

	@Before
	public void setUp() throws Exception {
		cut = new User();
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
	public void testFullName() {
		assertNull(cut.getFullName());
		
		cut.setFullName("foo");
		assertEquals("foo", cut.getFullName());
	}

	@Test
	public void testEmail() {
		assertNull(cut.getEmail());
		
		cut.setEmail("foo");
		assertEquals("foo", cut.getEmail());
	}

	@Test
	public void testPassword() {
		assertNull(cut.getPassword());
		
		cut.setPassword("foo");
		assertEquals("foo", cut.getPassword());
	}

	@Test
	public void testToString() {
		cut.setFullName("foo");
		assertEquals("foo", cut.toString());
	}
	
}
