package com.rh;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

// This could be an interface if CAMEL-6014 is fixed.

@Path("/")
public class MockServices {

	@GET
	@Path("employees/{id}")
	@Produces("application/json")
	public String getEmployeeInfo(@PathParam("id") String id, @QueryParam("fields") String fields) {
		return null;
	}

	@POST
	@Path("employees/{id}/requests")
	@Produces("application/json")
	public String createRequest(@PathParam("id") String id) {
		return null;
	}

	@GET
	@Produces("application/json")
	public String iAmAlive() {
		return null;
	}

}
