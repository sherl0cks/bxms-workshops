package com.rhc.bpm;

import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApprovalProcessTest extends AbstractBpmServiceTest {

	@Autowired
	private ProcessService processService;
	@Autowired
	private RuntimeDataService runtimeDataService;
	@Autowired
	private DeploymentService deploymentService;
	@Autowired
	private UserTaskService userTaskService;

	@Test
	public void test() {
		Assert.assertNotNull(processService);
		Assert.assertNotNull(runtimeDataService);
		Assert.assertNotNull(deploymentService);
		Assert.assertNotNull(userTaskService);
	}

}
