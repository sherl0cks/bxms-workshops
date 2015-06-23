package com.rhc.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.jbpm.services.task.query.TaskSummaryImpl;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;

import com.rhc.inbox.Tasks;

@Path("/tasks")
public class TasksEndpoint {

	private static List<TaskSummary> tasks = getTasks();

	@GET
	@Produces("application/json")
	public Tasks listAll(@QueryParam("start") final Integer startPosition, @QueryParam("max") final Integer maxResult) {

		return new Tasks(tasks);
	}

	@POST
	@Produces("application/json")
	public Tasks listAllPost( @Context HttpServletRequest request, InputStream requestBody ) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
		StringBuilder out = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			out.append(line);
		}
		System.out.println(out.toString()); // Prints the string content read
											// from input stream
		reader.close();

		return new Tasks(tasks);
	}

	private static List<TaskSummary> getTasks() {
		List<TaskSummary> tasks = new ArrayList<>();

		TaskSummaryImpl t = new TaskSummaryImpl();
		t.setName("fooooooooooo");
		t.setId(0l);
		t.setStatus(Status.Ready);
		tasks.add(t);

		TaskSummaryImpl t2 = new TaskSummaryImpl();
		t2.setName("bar");
		t2.setId(1l);
		t2.setStatus(Status.Ready);
		tasks.add(t2);

		return tasks;
	}

}
