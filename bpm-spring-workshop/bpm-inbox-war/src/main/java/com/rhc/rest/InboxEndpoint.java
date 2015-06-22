package com.rhc.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.rhc.inbox.Inbox;

@Path("/inboxes")
public class InboxEndpoint {

	private static Long CURRENT_ID = 0l;

	private static List<Inbox> inboxes = new ArrayList<Inbox>();

	@POST
	@Consumes("application/json")
	public Response create(final Inbox inbox) {

		inbox.setId(CURRENT_ID++);
		inboxes.add(inbox);

		return Response.created(
				UriBuilder.fromResource(InboxEndpoint.class)
						.path(String.valueOf(inbox.getId())).build()).build();
	}
	


	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") final Long id) {
		System.err.println("hssi");
		try {
			Inbox inbox = inboxes.get(id.intValue());
			return Response.ok(inbox).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Produces("application/json")
	public List<Inbox> listAll(
			@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {

		System.err.println("it's alive and connected!");
		if (inboxes.isEmpty()) {
			Inbox inbox = new Inbox(CURRENT_ID++);
			inboxes.add(inbox);
		}

		return inboxes;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, final Inbox inbox) {
		try {
			Inbox existingInbox = inboxes.get(id.intValue());
			inbox.setId(id);
			existingInbox = inbox;
			return Response.ok(existingInbox).build();
		} catch (Exception e) {
			return Response.noContent().build();
		}
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		try {
			inboxes.remove(id.intValue());
			return Response.noContent().build();
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
