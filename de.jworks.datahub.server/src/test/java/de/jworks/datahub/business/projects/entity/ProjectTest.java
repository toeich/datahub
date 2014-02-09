package de.jworks.datahub.business.projects.entity;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import de.jworks.datahub.business.projects.entity.Project;

public class ProjectTest {
	
	private Project cut;

	@Before
	public void setUp() throws Exception {
		cut = new Project();
	}

	@Test
	public void testId() {
		assertEquals(null, cut.getId());
	}

	@Test
	public void testName() {
		cut.setName("name");
		assertEquals("name", cut.getName());
	}

	@Test
	public void testDescription() {
		cut.setDescription("description");
		assertEquals("description", cut.getDescription());
	}

	@Test
	public void testLocalizedNames() {
		cut.getLocalizedNames().put("locale", "localizedName");
		assertEquals("localizedName", cut.getLocalizedNames().get("locale"));
	}

	@Test
	public void testLocalizedDescriptions() {
		cut.getLocalizedDescriptions().put("locale", "localizedDescription");
		assertEquals("localizedDescription", cut.getLocalizedDescriptions().get("locale"));
	}
	
	@Test
	public void testAcl() {
		assertNotEquals(cut.getAcl(), null);
	}

	@Test
	public void testToString() {
		cut.setName("a");
		assertEquals("a", cut.toString());
		
		cut.getLocalizedNames().put(Locale.getDefault().toString(), "b");
		assertEquals("b", cut.toString());
	}
	
}
