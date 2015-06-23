package com.rhc.rest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jbpm.services.api.model.ProcessInstanceDesc;
import org.jbpm.services.api.model.UserTaskInstanceDesc;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.redhat.approval.entities.Employee;
import com.redhat.approval.entities.VacationRequest;
import com.redhat.bpm.approval.VacationRequestService;

@Component
@Path("/requests")
public class VacationRequestEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(VacationRequestEndpoint.class);

	@Autowired
	private VacationRequestService vacationRequestService;

	@Context
	private UriInfo uriInfo;

	@POST
	@Consumes({ "application/x-www-form-urlencoded" })
	public Response create(@FormParam("first-name") String firstName, @FormParam("last-name") String lastName, @FormParam("userid") String userId,
			@FormParam("start-date") String startDate, @FormParam("end-date") String endDate) {

		VacationRequest vacationRequest = new VacationRequest();

		String[] start = startDate.split("-");
		vacationRequest.setStartDate(new LocalDate(Integer.valueOf(start[0]), Integer.valueOf(start[1]), Integer.valueOf(start[2])));

		String[] end = endDate.split("-");
		vacationRequest.setEndDate(new LocalDate(Integer.valueOf(end[0]), Integer.valueOf(end[1]), Integer.valueOf(end[2])));

		Employee employee = new Employee(userId, firstName, lastName);
		vacationRequest.setEmployee(employee);

		Long processInstanceId = vacationRequestService.startProcess(vacationRequest);

		URI uri = UriBuilder.fromPath(uriInfo.getBaseUri() + "reqests/" + processInstanceId.toString()).build();

		LOGGER.info(uri.toString());

		return Response.created(uri).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({ "application/json" })
	public Response getProcess(@PathParam("id") final Long id) {
		ProcessInstanceDesc instance = vacationRequestService.getRuntimeDataService().getProcessInstanceById(id);
		if (instance == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok(vacationRequestService.getRuntimeDataService().getProcessInstanceById(id)).build();
		}
	}

}
