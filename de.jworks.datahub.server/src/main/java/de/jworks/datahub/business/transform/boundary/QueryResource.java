package de.jworks.datahub.business.transform.boundary;

import java.io.StringReader;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import de.jworks.datahub.business.transform.controller.StylesheetBuilder;
import de.jworks.datahub.business.transform.entity.Transformation;

@Path("query/{queryId}")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class QueryResource {
	
	@Context 
	UriInfo uriInfo;
	
	@Inject
	TransformationService transformationService;

	@GET
	public Response query(@PathParam("queryId") long queryId) {
		try {
			Transformation query = transformationService.getTransformation(queryId);
			
			Source stylesheet = StylesheetBuilder.buildStylesheet(query);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer(stylesheet);
			StringWriter stringWriter = new StringWriter();
			transformer.transform(new StreamSource(new StringReader("<data/>")), new StreamResult(stringWriter));
			
			return Response.ok(stringWriter.toString(), MediaType.APPLICATION_XML).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
}
