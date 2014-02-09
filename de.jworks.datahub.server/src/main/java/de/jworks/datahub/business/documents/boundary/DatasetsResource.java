package de.jworks.datahub.business.documents.boundary;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("datasets")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class DatasetsResource {
	
	@GET
	public Response getDatasets(@Context UriInfo uriInfo) {
		return Response.ok().build();
	}
	
	@GET
	@Path("{id}")
	public Response getDocument(@PathParam("id") long documentId) {
		return Response.ok().build();
	}
	
	@GET
	@Path("{id}/content")
	public Response getDocumentContent(@PathParam("id") long documentId) {
		return Response.ok().build();
	}
	
}
