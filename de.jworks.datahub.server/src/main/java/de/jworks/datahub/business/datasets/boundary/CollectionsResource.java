package de.jworks.datahub.business.datasets.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.jworks.datahub.business.datasets.controller.SchemaGenerator;
import de.jworks.datahub.business.datasets.entity.Dataset;
import de.jworks.datahub.business.datasets.entity.DatasetGroup;

@Path("collections")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class CollectionsResource {

	@Inject
	DatasetService documentService;

	@Inject
	SchemaGenerator schemaGenerator;

	/*
	 * REST-API: [GET] rest/collections
	 */
	@GET
	public Response getCollections() {
		return Response.ok(new DocumentCollections(documentService.getDatasetGroups())).build();
	}
	
	/*
	 * REST-API: [POST] rest/collections
	 */
	@POST
	public Response addCollection(DatasetGroup collection) {
		documentService.addCollection(collection);
		return Response.ok(collection).build();
	}

	/*
	 * REST-API: [PUT] rest/collections // TODO: http://restcookbook.com/HTTP%20Methods/put-vs-post/
	 */
	@PUT
	public Response updateCollection(DatasetGroup collection) {
		documentService.updateCollection(collection);
		return Response.ok(collection).build();
	}
	
	/*
	 * REST-API: [GET] rest/collections/{collectionId}
	 */
	@GET
	@Path("{id}")
	public Response getCollection(@PathParam("id") long collectionId) {
		return Response.ok(documentService.getCollection(collectionId)).build();
	}

	/*
	 * REST-API: [DELETE] rest/collections/{collectionId}
	 */
	@DELETE
	@Path("{id}")
	public Response deleteCollection(@PathParam("id") long collectionId) {
		documentService.removeCollection(documentService.getCollection(collectionId));
		return Response.ok().build();
	}
	
	/*
	 * REST-API: [GET] rest/collections/{collectionId}/schema
	 */
	@GET
	@Path("{id}/schema")
	public Response getCollectionSchema(@PathParam("id") long collectionId) {
		DatasetGroup collection = documentService.getCollection(collectionId);
		return Response.ok(collection.getSchema()).build();
	}

	/*
	 * REST-API: [PUT] rest/collections/{collectionId}/schema
	 */
	@GET
	@Path("{id}/schema")
	public Response updateCollectionSchema(@PathParam("id") long collectionId, String xml) {
		DatasetGroup collection = documentService.getCollection(collectionId);
		return Response.ok().build();
	}

	/*
	 * REST-API: [GET] rest/collections/{collectionId}/schema
	 */
	@GET
	@Path("{id}/xsd")
	public Response getCollectionXsd(@PathParam("id") long collectionId) {
		DatasetGroup collection = documentService.getCollection(collectionId);
		String schema = schemaGenerator.generate(collection.getSchema().getRootElement());
		return Response.ok(schema, MediaType.APPLICATION_XML_TYPE).build();
	}

	/*
	 * REST-API: [GET] rest/collections/{collectionId}/documents
	 */
	@GET
	@Path("{id}/documents")
	public Response getCollectionDocuments(@PathParam("id") long collectionId) {
		List<Dataset> documents = new ArrayList<Dataset>();
		for (int i = 0; i < 100000; i++) {
			documents.add(new Dataset());
		}
		return Response.ok(new Documents(documents)).build();
	}

	/*
	 * REST-API: [POST] rest/collections/{collectionId}/documents
	 */
	@POST
	@Path("{id}/documents")
	public Response addDocument(@PathParam("id") long collectionId, String xml) {
		return Response.ok().build();
	}
	
	@XmlRootElement(name = "collections")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class DocumentCollections {

		@XmlElement(name = "collection")
		private List<DatasetGroup> collections;
		
		@SuppressWarnings("unused")
		private DocumentCollections() {}
		
		public DocumentCollections(List<DatasetGroup> collections) {
			this.collections = collections;
		}

	}

	@XmlRootElement(name = "documents")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Documents {

		@XmlElement(name = "document")
		private List<Dataset> documents;

		@SuppressWarnings("unused")
		private Documents() {}

		public Documents(List<Dataset> documents) {
			this.documents = documents;
		}

	}

}
