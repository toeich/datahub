package de.jworks.datahub.business.projects.boundary;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.jworks.datahub.business.common.entity.Project;

@Path("projects")
public class ProjectsResource {

	@GET
	public Response getProjects(@Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_XML })
	public Response createProject(Project project, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
}
