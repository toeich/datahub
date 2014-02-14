package de.jworks.datahub.business.datasets.boundary;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
		// TODO implement search
		return Response.ok().build();
	}
	
}
