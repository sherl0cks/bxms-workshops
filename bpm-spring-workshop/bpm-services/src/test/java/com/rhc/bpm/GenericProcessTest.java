package com.rhc.bpm;

import java.util.Collection;

import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.NodeInstanceDesc;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.query.QueryContext;
import org.springframework.beans.factory.annotation.Autowired;

public class GenericProcessTest extends AbstractBpmServiceTest {

	@Autowired
	private ProcessService processService;
	@Autowired
	private RuntimeDataService runtimeDataService;
	@Autowired
	private DeploymentService deploymentService;
	@Autowired
	private UserTaskService userTaskService;

	@Test
	public void shouldDeployAndUndeployANewUnit() {
		// given
		Collection<DeployedUnit> deployedUnits = deploymentService.getDeployedUnits();
		Assert.assertEquals(0, deployedUnits.size());

		// when
		deploymentService.deploy(DEPLOYMENT_UNIT);

		// then
		Assert.assertEquals(1, deployedUnits.size());
	}

	@Test
	public void shouldStartAndCompleteAProcess() {
		// given
		deploymentService.deploy(DEPLOYMENT_UNIT);

		// when
		Long id = processService.startProcess(DEPLOYMENT_UNIT.getIdentifier(), PROCESS_ID);

		// then
		ProcessInstance instance = processService.getProcessInstance(id);

		// completed processes comeback null
		Assert.assertNull(instance);

		// so lets check audit data
		Collection<NodeInstanceDesc> auditData = runtimeDataService.getProcessInstanceHistoryCompleted(id, new QueryContext());
		Assert.assertEquals(3, auditData.size());
	}
	
	
	@After
	public void cleanupDeployments(){
		deploymentService.undeploy( DEPLOYMENT_UNIT );
	}

}
