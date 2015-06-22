package com.rhc.bpm;

import java.util.Collection;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.model.DeployedUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.services.api.model.NodeInstanceDesc;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.builder.helper.FluentKieModuleDeploymentHelper;
import org.kie.api.builder.helper.KieModuleDeploymentHelper;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.query.QueryContext;

import com.rhc.utiils.TestUtils;

public class GenericProcessTest extends AbstractBpmServiceTest {

	protected static final String GROUP_ID = "com.redhat.workshops";
	protected static final String ARTIFACT_ID = "test-knowledge";
	protected static final String VERSION = "1.0.0";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
	protected static final String PROCESS_ID = "Ruleflow";

	@BeforeClass
	public static void buildTestRules() {
		FluentKieModuleDeploymentHelper helper1 = KieModuleDeploymentHelper.newFluentInstance();
		TestUtils.createDefaultKieBase(helper1);
		helper1.setGroupId(GROUP_ID).setArtifactId(ARTIFACT_ID).setVersion(VERSION).addResourceFilePath("Rule.drl")
				.addResourceFilePath("Ruleflow.bpmn").createKieJarAndDeployToMaven();
	}

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
		Collection<NodeInstanceDesc> auditData = runtimeDataService.getProcessInstanceHistoryCompleted(id,
				new QueryContext());
		Assert.assertEquals(3, auditData.size());
	}

	@After
	public void cleanupDeployments() {
		deploymentService.undeploy(DEPLOYMENT_UNIT);
	}

}
