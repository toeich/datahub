package de.jworks.datahub.business.tasks.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

@Path("tasks/{taskId}")
public class TaskResource {
	
	@GET
	public Response getTask(@PathParam("taskId") String taskId, @Context UriInfo uriInfo) {
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
