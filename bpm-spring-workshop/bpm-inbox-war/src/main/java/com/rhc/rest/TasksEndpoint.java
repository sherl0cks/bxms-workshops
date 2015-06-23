package com.rhc.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jbpm.services.task.query.TaskSummaryImpl;
import org.kie.api.task.model.Status;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.query.QueryFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redhat.bpm.approval.VacationRequestService;
import com.rhc.inbox.Tasks;

@Component
@Path("/tasks")
public class TasksEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskEndpoint.class);

	@Autowired
	private VacationRequestService vacationRequestService;

	private static List<TaskSummary> tasks = getTasks();

	@GET
	@Produces("application/json")
	public Tasks listAll(@QueryParam("start") final String startPosition, @QueryParam("max") final Integer maxResult) {

		// List<TaskSummary> taskSummaries =
		// vacationRequestService.getRuntimeDataService().get

		return new Tasks(tasks);
	}

	@POST
	@Produces("application/json")
	@Consumes({ "application/x-www-form-urlencoded", "application/json" })
	public Tasks listAllPost(@FormParam("searchPhrase") final String searchPhrase) throws IOException {

		Tasks tasks = null;
		if (searchPhrase == null || searchPhrase.isEmpty()) {
			LOGGER.info("phrase is null");
			List<String> groups = new ArrayList<>();
			groups.add("task_admin");
			tasks = new Tasks(vacationRequestService.getRuntimeDataService().getTasksAssignedAsPotentialOwner("jholmes", groups, new QueryFilter()));
			LOGGER.info(tasks.toString());
			return tasks;
		} else {
			LOGGER.info("phrase is " + searchPhrase);
			tasks = new Tasks(vacationRequestService.getRuntimeDataService().getTasksAssignedAsPotentialOwner(searchPhrase, new QueryFilter()));
			LOGGER.info(tasks.toString());
			return tasks;
		}
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
