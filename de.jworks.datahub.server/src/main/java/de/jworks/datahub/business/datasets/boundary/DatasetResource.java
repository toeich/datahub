package de.jworks.datahub.business.datasets.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("datasets/{datasetId}")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class DatasetResource {
	
	@Inject
	DatasetService datasetService;
	
	@GET
	public Response getDataset(@PathParam("datasetId") long datasetId) {
		return Response.ok(datasetService.getDataset(datasetId)).build();
	}
	
}
