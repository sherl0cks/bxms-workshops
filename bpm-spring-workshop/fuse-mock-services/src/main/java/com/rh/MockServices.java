package com.rh;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

// This could be an interface if CAMEL-6014 is fixed.

@Path("/")
public class MockServices {

	@POST
	@Path("employees/{id}/requests")
	@Produces("application/json")
	public String createRequest(@PathParam("id") String id) {
		return null;
	}

	@POST
	@Path("requests/{id}/status")
	@Produces("application/json")
	public String updateStatus(@PathParam("id") String id) {
		return null;
	}

	@GET
	@Produces("application/json")
	public String iAmAlive() {
		return null;
	}

}
