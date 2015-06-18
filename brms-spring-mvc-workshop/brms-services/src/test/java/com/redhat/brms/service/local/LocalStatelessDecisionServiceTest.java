package com.redhat.brms.service.local;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.builder.helper.FluentKieModuleDeploymentHelper;
import org.kie.api.builder.helper.KieModuleDeploymentHelper;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.builder.model.KieSessionModel.KieSessionType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.service.api.StatelessDecisionService;

@ActiveProfiles(profiles = { "test-local" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class LocalStatelessDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "localDecisionService")
	private StatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	@BeforeClass
	public static void buildTestKJars() {
		FluentKieModuleDeploymentHelper helper1 = KieModuleDeploymentHelper.newFluentInstance();
		createDefaultKieBase(helper1);
		helper1.setGroupId("com.redhat.workshops").setArtifactId("test-knowledge").setVersion("2.0").addResourceFilePath("Rule.drl").addResourceFilePath("Ruleflow.bpmn")
				.createKieJarAndDeployToMaven();

		FluentKieModuleDeploymentHelper helper2 = KieModuleDeploymentHelper.newFluentInstance();
		createDefaultKieBase(helper2);
		helper2.setGroupId("com.redhat.workshops").setArtifactId("test-knowledge").setVersion("2.1").addResourceFilePath("Rule.drl").addResourceFilePath("Ruleflow.bpmn")
				.createKieJarAndDeployToMaven();
	}

	@Test
	public void shouldLoadWorkshopRulesAndExecuteLocally() {
		Collection<Object> facts = new ArrayList<Object>();

		boolean result = decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "test-knowledge", "2.0");
		Assert.assertTrue(result);
		decisionService.execute(facts, "Ruleflow");

		result = decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "test-knowledge", "2.1");
		Assert.assertTrue(result);
		decisionService.execute(facts, "Ruleflow");
	}

	private static void createDefaultKieBase(FluentKieModuleDeploymentHelper helper) {
		KieBaseModel kieBaseModel = helper.getKieModuleModel().newKieBaseModel("defaultKieBase").addPackage("*").setDefault(true);
		kieBaseModel.newKieSessionModel("defaultKieSession").setDefault(true);
		kieBaseModel.newKieSessionModel("defaultStatelessKieSession").setType(KieSessionModel.KieSessionType.STATELESS).setDefault(true);
	}
}
