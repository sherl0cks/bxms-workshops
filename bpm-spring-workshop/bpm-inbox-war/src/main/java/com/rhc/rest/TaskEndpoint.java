package com.rhc.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
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

import com.rhc.inbox.Task;

@RequestScoped
@Path("/taskssssss")
public class TaskEndpoint {

	@POST
	@Consumes({ "application/xml", "application/json" })
	public Response create(final Task task) {
		//TODO: process the given task 
		//you may want to use the following return statement, assuming that Task#getId() or a similar method 
		//would provide the identifier to retrieve the created Task resource:
		//return Response.created(UriBuilder.fromResource(TaskEndpoint.class).path(String.valueOf(task.getId())).build()).build();
		return Response.created(null).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/xml", "application/json" })
	public Response findById(@PathParam("id") final Long id) {
		//TODO: retrieve the task 
		Task task = null;
		if (task == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(task).build();
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Task> listAll(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {
		//TODO: retrieve the tasks 
		final List<Task> tasks = null;
		return tasks;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes({ "application/xml", "application/json" })
	public Response update(@PathParam("id") Long id, final Task task) {
		//TODO: process the given task 
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the task matching by the given id 
		return Response.noContent().build();
	}

}
