package com.redhat.steps;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.services.api.model.DeploymentUnit;
import org.junit.Assert;
import org.kie.api.runtime.process.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

import bitronix.tm.resource.jdbc.PoolingDataSource;

import com.redhat.approval.entities.VacationRequest;
import com.redhat.bpm.approval.VacationRequestService;
import com.redhat.utils.TestDataUtil;
import com.rhc.utiils.TestUtils;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CucumberSteps {

	protected static final String GROUP_ID = "com.redhat.workshops";
	protected static final String ARTIFACT_ID = "approval-knowledge";
	protected static final String VERSION = "1.0.0-SNAPSHOT";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
	protected static final String PROCESS_ID = "com.redhat.workshops.VacationApproval";

	protected static final String MANAGER_ID = "msmith";
	protected static final String EMPLOYEE_ID = "tbrown";

	@Autowired
	protected VacationRequestService vacationRequestService;

	private VacationRequest request;
	private long processInstanceId;

	@Autowired
	protected ProcessService processService;
	@Autowired
	protected RuntimeDataService runtimeDataService;
	@Autowired
	protected DeploymentService deploymentService;
	@Autowired
	protected UserTaskService userTaskService;

	protected static PoolingDataSource pds;

	@Before
	public void setup() {
		TestUtils.cleanupSingletonSessionId();
		request = null;
		processInstanceId = 0;

	}

	@After
	public void cleanupDeployments() {
		deploymentService.undeploy(DEPLOYMENT_UNIT);
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
		processInstanceId = vacationRequestService.startProcess(request);

	}

	@When("^my manager approves the request$")
	public void my_manager_approves_the_request() throws Throwable {
		vacationRequestService.approveTheRequest(MANAGER_ID);

	}

	@When("^my manager asks for more information$")
	public void my_manager_asks_for_more_information() throws Throwable {
		vacationRequestService.needMoreInfoOnTheRequest(MANAGER_ID,
				"There is a big meeting during this time, why is this important?");

	}

	@When("^I reply with a acceptable answer$")
	public void i_reply_with_a_acceptable_answer() throws Throwable {
		vacationRequestService
				.provideMoreInformation(EMPLOYEE_ID, "I need to take my daughter to college orientation.");

	}

	@Then("^the request is approved$")
	public void the_request_is_approved() throws Throwable {
		Assert.assertTrue(vacationRequestService.getRuntimeDataService().getProcessInstanceById(processInstanceId)
				.getState() == ProcessInstance.STATE_COMPLETED);
	}

	@Then("^the request is not approved$")
	public void the_request_is_not_approved() throws Throwable {
		Assert.assertTrue(vacationRequestService.getRuntimeDataService().getProcessInstanceById(processInstanceId)
				.getState() == ProcessInstance.STATE_ACTIVE);
		// complete task so deployment unit can be undeployed
		my_manager_approves_the_request();
	}

}
