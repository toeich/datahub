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

@Path("collection")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class CollectionResource {
	
	@Inject 
	CollectionService collectionService;

	@GET
	@Path("{id}")
	public Response getCollection(@PathParam("id") long collectionId) {
		DatasetGroup _collection = collectionService.getCollection(collectionId);
		return Response.ok(_collection).build();
	}
	
	@PUT
	@Path("{id}")
	public Response updateCollection(@PathParam("id") long collectionId, DatasetGroup collection) {
		DatasetGroup _collection = collectionService.getCollection(collectionId);
		_collection.setName(collection.getName());
		_collection.setDescription(collection.getDescription());
		collectionService.updateCollection(_collection);
		return Response.ok(_collection).build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteCollection(@PathParam("id") long collectionId) {
		DatasetGroup _collection = collectionService.getCollection(collectionId);
		collectionService.removeCollection(_collection);
		return Response.ok().build();
	}
	
}
