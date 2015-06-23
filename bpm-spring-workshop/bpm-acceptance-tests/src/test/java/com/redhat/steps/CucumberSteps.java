package com.redhat.steps;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.kie.api.runtime.process.ProcessInstance;

import com.redhat.approval.entities.VacationRequest;
import com.redhat.utils.TestDataUtil;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CucumberSteps {

	private static final String ROOT_URL = "http://bpmworkshop-rhcbap.rhcloud.com/rest/requests";

	private VacationRequest request;
	private String processInstanceURI;

	@Before
	public void setup() {
		request = null;
		processInstanceURI = null;

	}

	@Given("^a vacation request that is (\\d+) day long$")
	public void a_vacation_request_that_is_day_long(int days) throws Throwable {
		a_vacation_request_that_is_days_long(days);
	}

	@Given("^a vacation request that is (\\d+) days long$")
	public void a_vacation_request_that_is_days_long(int days) throws Throwable {
		request = TestDataUtil.makeVacationRequest(days);
	}

	@When("^I submit the vacation request$")
	public void i_submit_the_vacation_request() throws Throwable {
		Client client = ClientBuilder.newClient();

		WebTarget create = client.target(ROOT_URL);
		Form form = new Form();
		form.param("first-name", request.getEmployee().getFirstName());
		form.param("last-name", request.getEmployee().getLastName());
		form.param("userid", request.getEmployee().getUserId());
		form.param("start-date", makeDateString(request.getStartDate()));
		form.param("end-date", makeDateString(request.getEndDate()));

		Response post = create.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		Assert.assertTrue(post.getStatus() == 201);

		processInstanceURI = post.getHeaderString("location");
	}

	@When("^my manager approves the request$")
	public void my_manager_approves_the_request() throws Throwable {
		Client client = ClientBuilder.newClient();
		WebTarget create = client.target(processInstanceURI + "/approve");
		Response put = create.request().put(null);
		Assert.assertTrue(put.getStatus() == 200);
	}

	@When("^my manager asks for more information$")
	public void my_manager_asks_for_more_information() throws Throwable {
		Client client = ClientBuilder.newClient();
		WebTarget create = client.target(processInstanceURI + "/comment?user=manager");
		Entity<String> entity = Entity.json("There is a big meeting during this time, why is this important?");
		Response put = create.request().put(entity);
		Assert.assertTrue(put.getStatus() == 200);
	}

	@When("^I reply with a acceptable answer$")
	public void i_reply_with_a_acceptable_answer() throws Throwable {
		Client client = ClientBuilder.newClient();
		WebTarget create = client.target(processInstanceURI + "/comment?user=employee");
		Entity<String> entity = Entity.json("I need to take my daughter to college orientation.");
		Response put = create.request().put(entity);
		Assert.assertTrue(put.getStatus() == 200);
	}

	@Then("^the request is approved$")
	public void the_request_is_approved() throws Throwable {
		Assert.assertTrue(getProcessInstanceState() == ProcessInstance.STATE_COMPLETED);

	}

	@Then("^the request is not approved$")
	public void the_request_is_not_approved() throws Throwable {
		Assert.assertTrue(getProcessInstanceState() == ProcessInstance.STATE_ACTIVE);
	}

	public int getProcessInstanceState() {
		Client client = ClientBuilder.newClient();
		WebTarget create = client.target(processInstanceURI);
		Response get = create.request().get();
		String json = get.readEntity(String.class);
		return Integer.parseInt(json.substring(json.indexOf("\"state\"") + 8, json.indexOf("\"state\"") + 9));
	}

	public String makeDateString(LocalDate date) {
		String dateStr = "";
		dateStr = date.getYear() + "-" + date.getMonthOfYear() + "-" + date.getDayOfMonth();
		return dateStr;
	}

}
