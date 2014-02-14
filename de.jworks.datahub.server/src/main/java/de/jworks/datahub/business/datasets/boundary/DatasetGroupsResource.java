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

@Path("datasetgroups")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class DatasetGroupsResource {

	@Inject
	DatasetService datasetService;

	@Inject
	SchemaGenerator schemaGenerator;

	/*
	 * REST-API: [GET] rest/datasetgroups
	 */
	@GET
	public Response getDatasetGroups() {
		return Response.ok(new DatasetGroups(datasetService.getDatasetGroups())).build();
	}
	
	/*
	 * REST-API: [POST] rest/datasetgroups
	 */
	@POST
	public Response addDatasetGroup(DatasetGroup collection) {
		datasetService.addDatasetGroup(collection);
		return Response.ok(collection).build();
	}

	/*
	 * REST-API: [PUT] rest/datasetgroups // TODO: http://restcookbook.com/HTTP%20Methods/put-vs-post/
	 */
	@PUT
	public Response updateDatasetGroup(DatasetGroup collection) {
		datasetService.updateDatasetGroup(collection);
		return Response.ok(collection).build();
	}
	
	/*
	 * REST-API: [GET] rest/datasetgroups/{datasetGroupId}
	 */
	@GET
	@Path("{id}")
	public Response getDatasetGroup(@PathParam("id") long datasetGroupId) {
		return Response.ok(datasetService.getDatasetGroup(datasetGroupId)).build();
	}

	/*
	 * REST-API: [DELETE] rest/datasetgroups/{datasetGroupId}
	 */
	@DELETE
	@Path("{id}")
	public Response deleteDatasetGroup(@PathParam("id") long datasetGroupId) {
		datasetService.removeDatasetGroup(datasetService.getDatasetGroup(datasetGroupId));
		return Response.ok().build();
	}
	
	/*
	 * REST-API: [GET] rest/datasetgroups/{datasetGroupId}/schema
	 */
	@GET
	@Path("{id}/schema")
	public Response getDatasetGroupSchema(@PathParam("id") long datasetGroupId) {
		DatasetGroup collection = datasetService.getDatasetGroup(datasetGroupId);
		return Response.ok(collection.getSchema()).build();
	}

	/*
	 * REST-API: [PUT] rest/datasetgroups/{datasetGroupId}/schema
	 */
	@GET
	@Path("{id}/schema")
	public Response updateDatasetGroupSchema(@PathParam("id") long datasetGroupId, String xml) {
		DatasetGroup collection = datasetService.getDatasetGroup(datasetGroupId);
		return Response.ok().build();
	}

	/*
	 * REST-API: [GET] rest/datasetgroups/{datasetGroupId}/xsd
	 */
	@GET
	@Path("{id}/xsd")
	public Response getDatasetGroupXsd(@PathParam("id") long datasetGroupId) {
		DatasetGroup collection = datasetService.getDatasetGroup(datasetGroupId);
		String schema = schemaGenerator.generate(collection.getSchema().getRootElement());
		return Response.ok(schema, MediaType.APPLICATION_XML_TYPE).build();
	}

	/*
	 * REST-API: [GET] rest/datasetgroups/{datasetGroupId}/datasets
	 */
	@GET
	@Path("{id}/datasets")
	public Response getDatasetGroupDocuments(@PathParam("id") long datasetGroupId) {
		List<Dataset> documents = new ArrayList<Dataset>();
		for (int i = 0; i < 100000; i++) {
			documents.add(new Dataset());
		}
		return Response.ok(new Datasets(documents)).build();
	}

	/*
	 * REST-API: [POST] rest/datasetgroups/{datasetGroupId}/datasets
	 */
	@POST
	@Path("{id}/documents")
	public Response addDocument(@PathParam("id") long datasetGroupId, String xml) {
		return Response.ok().build();
	}
	
	@XmlRootElement(name = "datasetgroups")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class DatasetGroups {

		@XmlElement(name = "datasetgroup")
		private List<DatasetGroup> datasetgroups;
		
		@SuppressWarnings("unused")
		private DatasetGroups() {}
		
		public DatasetGroups(List<DatasetGroup> datasetgroups) {
			this.datasetgroups = datasetgroups;
		}

	}

	@XmlRootElement(name = "datasets")
	@XmlAccessorType(XmlAccessType.NONE)
	private static class Datasets {

		@XmlElement(name = "dataset")
		private List<Dataset> datasets;

		@SuppressWarnings("unused")
		private Datasets() {}

		public Datasets(List<Dataset> datasets) {
			this.datasets = datasets;
		}

	}

}
