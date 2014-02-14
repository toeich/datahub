package de.jworks.datahub.business.datasets.boundary;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.jworks.datahub.business.datasets.entity.DatasetGroup;

@Path("datasetgroups/{datasetGroupId}")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class DatasetGroupResource {
	
	@Inject 
	DatasetService datasetService;

	@GET
	public Response getDatasetGroup(@PathParam("datasetGroupId") long datasetGroupId) {
		return Response.ok(datasetService.getDatasetGroup(datasetGroupId)).build();
	}
	
	@PUT
	public Response updateDatasetGroup(@PathParam("datasetGroupId") long datasetGroupId, DatasetGroup datasetGroup) {
		return Response.ok(datasetService.updateDatasetGroup(datasetGroup)).build();
	}

	@DELETE
	public Response deleteDatasetGroup(@PathParam("datasetGroupId") long datasetGroupId) {
		datasetService.removeDatasetGroup(datasetService.getDatasetGroup(datasetGroupId));
		return Response.ok().build();
	}
	
}
