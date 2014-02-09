package de.jworks.datahub.business.tasks.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("tasks")
public class TasksResource {
	
	@GET
	public Response getTasks(@Context UriInfo uriInfo) {
		return Response.ok(uriInfo.getRequestUri().toString()).build();
	}
	
}
