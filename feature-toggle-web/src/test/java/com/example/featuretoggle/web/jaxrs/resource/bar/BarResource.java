package com.example.featuretoggle.web.jaxrs.resource.bar;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/bar")
@Produces({MediaType.APPLICATION_JSON})
public interface BarResource {
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getBar(
				@PathParam("id")
				String id);	
	
	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	public Response getBarByQuery(
				@QueryParam("query")
				String query);	

}