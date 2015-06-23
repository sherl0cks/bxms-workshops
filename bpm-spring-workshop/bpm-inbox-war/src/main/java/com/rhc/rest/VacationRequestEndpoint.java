package com.rhc.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jbpm.services.api.model.ProcessInstanceDesc;
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
	public Response create(@FormParam("first-name") String firstName, @FormParam("last-name") String lastName,
			@FormParam("userid") String userId, @FormParam("start-date") String startDate,
			@FormParam("end-date") String endDate) {

		VacationRequest vacationRequest = new VacationRequest();

		String[] start = startDate.split("-");
		vacationRequest.setStartDate(new LocalDate(Integer.valueOf(start[0]), Integer.valueOf(start[1]), Integer
				.valueOf(start[2])));

		String[] end = endDate.split("-");
		vacationRequest.setEndDate(new LocalDate(Integer.valueOf(end[0]), Integer.valueOf(end[1]), Integer
				.valueOf(end[2])));

		Employee employee = new Employee(userId, firstName, lastName);
		vacationRequest.setEmployee(employee);

		Long processInstanceId = vacationRequestService.startProcess(vacationRequest);

		URI uri = UriBuilder.fromPath(uriInfo.getBaseUri() + "requests/" + processInstanceId.toString()).build();

		LOGGER.info(uri.toString());

		return Response.created(uri).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json" })
	public Response getProcess(@PathParam("id") final Long id) {
		ProcessInstanceDesc instance = vacationRequestService.getRuntimeDataService().getProcessInstanceById(id);
		if (instance == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok(vacationRequestService.getRuntimeDataService().getProcessInstanceById(id)).build();
		}
	}

	@PUT
	@Path("/{id}/approve")
	public Response approveRequest(@PathParam("id") final Long id) {
		ProcessInstanceDesc instance = vacationRequestService.getRuntimeDataService().getProcessInstanceById(id);
		if (instance == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			String managerId = (String) vacationRequestService.getProcessService().getProcessInstanceVariable(id,
					"managerId");
			vacationRequestService.approveTheRequest(managerId);
			return Response.ok().build();
		}
	}

	@PUT
	@Path("/{id}/comment")
	public Response comment(@PathParam("id") final Long id, @QueryParam("user") String user, String comment) {
		ProcessInstanceDesc instance = vacationRequestService.getRuntimeDataService().getProcessInstanceById(id);
		if (instance == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			if (user.equals("manager")) {
				String managerId = (String) vacationRequestService.getProcessService().getProcessInstanceVariable(id,
						"managerId");
				vacationRequestService.needMoreInfoOnTheRequest(managerId, comment);
			} else {
				String employeeId = (String) vacationRequestService.getProcessService().getProcessInstanceVariable(id,
						"employeeId");
				LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!: " + employeeId);
				LOGGER.info(instance.toString());
				vacationRequestService.provideMoreInformation(employeeId, comment);
			}
			return Response.ok().build();
		}
	}
}
