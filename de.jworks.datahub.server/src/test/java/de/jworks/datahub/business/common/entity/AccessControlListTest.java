package de.jworks.datahub.business.common.entity;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.jworks.datahub.business.common.entity.AccessControlEntry;
import de.jworks.datahub.business.common.entity.AccessControlList;

public class AccessControlListTest {

	private AccessControlList cut;
	
	@Before
	public void setUp() throws Exception {
		cut = new AccessControlList();
	}

	@Test
	public void testEntries() {
		assertNotNull(cut.getEntries());
		assertEquals(0, cut.getEntries().size());
		
		cut.getEntries().add(new AccessControlEntry());
		assertEquals(1, cut.getEntries().size());
	}

}
