package com.rhc.bpm;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.examples.cmd.ForcedRetryCommand;
import org.jbpm.examples.cmd.PollForcedRetryCommand;
import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AsyncProcessTest extends AbstractBpmServiceTest {

	protected static final String GROUP_ID = "org.jbpm.example";
	protected static final String ARTIFACT_ID = "async-examples";
	protected static final String VERSION = "1.0.0-SNAPSHOT";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
	protected static final String PROCESS_ID = "AsyncRetryWithTimers";

	@BeforeClass
	public static void init() {
		if (System.getProperty("org.kie.deployment.desc.location") == null) {
			System.setProperty("org.kie.deployment.desc.location", "classpath:META-INF/kie-wb-deployment-descriptor.xml");
		}if (System.getProperty("org.kie.executor.interval") == null) {
			System.setProperty("org.kie.executor.interval", "1");
		}
	}

	@Test
	public void runAsyncTest() throws InterruptedException {
		// given
		deploymentService.deploy(DEPLOYMENT_UNIT);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sendCommand", "org.jbpm.examples.cmd.ForcedRetryCommand");
		params.put("pollCommand", "org.jbpm.examples.cmd.PollForcedRetryCommand");

		// when
		Long id = processService.startProcess(DEPLOYMENT_UNIT.getIdentifier(), PROCESS_ID, params);

		Thread.sleep(20000l);

		// then
		Assert.assertEquals(2, ForcedRetryCommand.executionCount);
		Assert.assertEquals(3, PollForcedRetryCommand.pollCount);
	}
}
