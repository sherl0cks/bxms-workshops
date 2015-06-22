package com.rhc.bpm;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.api.model.NodeInstanceDesc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.query.QueryContext;
import org.kie.internal.query.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.redhat.approval.entities.VacationRequest;
import com.redhat.bpm.approval.VacationRequestService;
import com.redhat.utils.TestDataUtil;

/**
 * 
 * This test the actually runs and deploys the approval knowledge for the
 * workshop
 *
 */
public class ApprovalProcessTest extends AbstractBpmServiceTest {

	protected static final String GROUP_ID = "com.redhat.workshops";
	protected static final String ARTIFACT_ID = "approval-knowledge";
	protected static final String VERSION = "1.0.0-SNAPSHOT";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
	protected static final String PROCESS_ID = "com.redhat.workshops.VacationApproval";

	protected static final String MANAGER_ID = "msmith";
	protected static final String EMPLOYEE_ID = "tbrown";

	@Autowired
	protected VacationRequestService vacationRequestService;

	@Test
	public void shouldDeployApprovalProcess() {
		// given
		Collection<DeployedUnit> deployedUnits = deploymentService.getDeployedUnits();
		Assert.assertEquals(0, deployedUnits.size());

		// when
		deploymentService.deploy(DEPLOYMENT_UNIT);

		// then
		Assert.assertEquals(1, deployedUnits.size());
	}

	@Test
	public void shouldStartApproveAndCompleteProcessesForLongRequest() throws InterruptedException {
		// Given
		deploymentService.deploy(DEPLOYMENT_UNIT);
		Map<String, Object> processData = TestDataUtil.getLongVacationRequest().toProcessDataMap();

		// when
		Long processInstanceId = processService.startProcess(DEPLOYMENT_UNIT.getIdentifier(), PROCESS_ID, processData);

		// then
		ProcessInstance instance = processService.getProcessInstance(processInstanceId);

		// completed processes comeback null
		Assert.assertNotNull(instance);

		vacationRequestService.approveTheRequest(MANAGER_ID);

		// so lets check audit data
		QueryContext context = new QueryContext();
		context.setCount( new Integer(100) );
		Collection<NodeInstanceDesc> auditData = runtimeDataService.getProcessInstanceHistoryCompleted(processInstanceId, context);
		Assert.assertEquals(10, auditData.size());
	}

	@Test
	public void shouldStartRequestMoreInfoAndCompleteProcessesForLongRequest() throws InterruptedException {
		// Given
		deploymentService.deploy(DEPLOYMENT_UNIT);
		Map<String, Object> processData = TestDataUtil.getLongVacationRequest().toProcessDataMap();

		
		Long processInstanceId = processService.startProcess(DEPLOYMENT_UNIT.getIdentifier(), PROCESS_ID, processData);


		ProcessInstance instance = processService.getProcessInstance(processInstanceId);

		// process is incomplete, will not be null
		Assert.assertNotNull(instance);

		// when
		// let's do some stuff on the process
		vacationRequestService.needMoreInfoOnTheRequest(MANAGER_ID, "There is a big meeting during this time, why is this important?");
		
		vacationRequestService.provideMoreInformation(EMPLOYEE_ID, "I need to take my daughter to college orientation.");
		
		vacationRequestService.approveTheRequest(MANAGER_ID);
		
		// and then check that we hit all the elements
		QueryContext context = new QueryContext();
		context.setCount( new Integer(100) );
		Collection<NodeInstanceDesc> auditData = runtimeDataService.getProcessInstanceHistoryCompleted(processInstanceId, context);
		Assert.assertEquals(14, auditData.size());
	}

	@Test
	public void shouldStartAndCompleteProcessesForShortRequest() throws InterruptedException {
		// Given
		deploymentService.deploy(DEPLOYMENT_UNIT);
		Map<String, Object> processData = TestDataUtil.getShortVacationRequest().toProcessDataMap();

		// when
		Long id = processService.startProcess(DEPLOYMENT_UNIT.getIdentifier(), PROCESS_ID, processData);

		// then
		ProcessInstance instance = processService.getProcessInstance(id);

		// completed processes comeback null
		Assert.assertNull(instance);

		// so lets check audit data
		Collection<NodeInstanceDesc> auditData = runtimeDataService.getProcessInstanceHistoryCompleted(id, new QueryContext());
		Assert.assertEquals(7, auditData.size());
	}

	@After
	public void cleanupDeployments() {
		deploymentService.undeploy(DEPLOYMENT_UNIT);
	}
}
