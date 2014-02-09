package de.jworks.datahub.business.search.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("search")
public class SearchResource {
	
	@Inject
	SearchService searchService;

	@GET
	@Path("{query}")
	public Response search(@PathParam("query") String query) {
		return Response.ok(searchService.search(query)).build();
	}
}
