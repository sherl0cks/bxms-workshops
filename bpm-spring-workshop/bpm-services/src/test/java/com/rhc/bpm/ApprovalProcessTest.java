package com.rhc.bpm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.api.model.NodeInstanceDesc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieContext;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.query.QueryContext;
import org.kie.internal.runtime.KnowledgeContext;

import com.rhc.test.TestDataUtil;

/**
 * 
 * This test the actually runs and deploys the approval knowledge for the workshop
 *
 */
public class ApprovalProcessTest extends AbstractBpmServiceTest {

	protected static final String GROUP_ID = "com.redhat.workshops";
	protected static final String ARTIFACT_ID = "approval-knowledge";
	protected static final String VERSION = "1.0.0-SNAPSHOT";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
	protected static final String PROCESS_ID = "com.redhat.workshops.VacationApproval";

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
	public void shouldStartAndCompleteProcesses() throws InterruptedException{
		// Given
		deploymentService.deploy(DEPLOYMENT_UNIT);
		Map<String, Object> processData = new HashMap<>();
		processData.put("vacationRequest", TestDataUtil.getVacationRequest() );
		
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
