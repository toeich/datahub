package de.jworks.datahub.business.projects.boundary;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import de.jworks.datahub.business.projects.boundary.ProjectService;

public class ProjectsServiceTest {
	
	private ProjectService cut;

	@Before
	public void setUp() throws Exception {
		cut = new ProjectService();
		cut.entityManager = mock(EntityManager.class);
	}

	@Test
	public void testGetProjects() {
//		fail("Not yet implemented");
	}

}
