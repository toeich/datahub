package de.jworks.datahub.business.projects.boundary;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import de.jworks.datahub.business.common.boundary.AccessControlService;
import de.jworks.datahub.business.common.entity.Permission;
import de.jworks.datahub.business.common.entity.Project;
import de.jworks.datahub.business.common.entity.Role;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;

@Stateless
public class ProjectService {
	
	@Inject
	EntityManager entityManager;
	
	@Inject
	AccessControlService accessControlService;

	public List<Project> getProjects() {
		List<Project> result = entityManager
				.createQuery("SELECT p FROM Project p", Project.class)
				.getResultList();
		accessControlService.filter(result, Permission.READ);
		return result;
	}
	
	public List<Project> getProjects(DatasetGroup datasetGroup) {
		List<Project> projects = entityManager.find(DatasetGroup.class, datasetGroup.getId()).getProjects();
		return projects.size() > 0 ? projects : getProjects();
	}

	public Project getProject(long projectId) {
		Project result = entityManager.find(Project.class, projectId);
		return accessControlService.hasPermission(result, "read") ? result : null; 
	}
	
	public Project findProjectByName(String name) {
		Project result = entityManager
				.createQuery("SELECT p FROM Project p WHERE p.name = :name", Project.class)
				.setParameter("name", name)
				.getSingleResult();
		return accessControlService.hasPermission(result, Permission.READ) ? result : null;
	}
	
	@RolesAllowed({ Role.ADMIN })
	public void addProject(Project project) {
	    entityManager.persist(project);
	}
	
    public void updateProject(Project project) {
        entityManager.merge(project);
    }
    
    public void removeProject(Project project) {
        entityManager.remove(entityManager.merge(project));
    }
    
}
