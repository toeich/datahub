package de.jworks.datahub.business.projects.boundary;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.jworks.datahub.business.common.entity.AccessControlList;
import de.jworks.datahub.business.common.entity.Project;

@Path("projects/{projectName}")
@Consumes({ MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_XML })
public class ProjectResource {

	@GET
	public Response getProject(@PathParam("projectName") String projectName, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
	@PUT
	public Response updateProject(@PathParam("projectName") String projectName, Project project, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
	@DELETE
	public Response deleteProject(@PathParam("projectName") String projectName, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
	@GET
	@Path("acl")
	public Response getProjectAcl(@PathParam("projectName") String projectName, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
	@PUT
	@Path("acl")
	public Response updateProjectAcl(@PathParam("projectName") String projectName, AccessControlList acl, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
	@DELETE
	@Path("acl")
	public Response deleteProjectAcl(@PathParam("projectName") String projectName, @Context UriInfo uriInfo) {
		return Response.status(Status.SERVICE_UNAVAILABLE).build();
	}
	
}
