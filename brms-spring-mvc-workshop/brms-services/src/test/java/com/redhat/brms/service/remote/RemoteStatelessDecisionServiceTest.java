package com.redhat.brms.service.remote;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerStatus;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ActiveProfiles(profiles = { "test-remote" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class RemoteStatelessDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RemoteStatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	@Test
	public void shouldLoadTestRulesWihoutRuleflow() {
		Assert.assertNotNull(decisionService);
		decisionService.setContainerId("test");
		decisionService.execute(null);
	}

	@Test
	public void shouldLoadTestRulesWithRuleflow() {
		Assert.assertNotNull(decisionService);
		decisionService.setContainerId("test2");
		Collection<Object> facts = new ArrayList<Object>();
		facts.add(new String("hello"));
		decisionService.execute(facts, "Ruleflow");
	}

	@Test
	public void shouldLoadTestRulesWithRuleflowAndEmptyFacts() {
		Assert.assertNotNull(decisionService);
		decisionService.setContainerId("test2");
		Collection<Object> facts = new ArrayList<Object>();
		decisionService.execute(facts, "Ruleflow");
		decisionService.execute(null, "Ruleflow");
	}

	@Test
	public void shouldCreateContainerAndUpgradeIt() {
		Assert.assertNotNull(decisionService);
		KieServicesClient client = decisionService.getClient();
		decisionService.setContainerId("test100");

		boolean response = decisionService.createOrUpgradeRulesWithVersion("com.rhc", "basic-business-rules", "1.0");
		Assert.assertTrue( response );

		decisionService.execute(null, "Ruleflow");
		
		boolean reponse = decisionService.createOrUpgradeRulesWithVersion("com.rhc", "basic-business-rules", "1.1");
		Assert.assertTrue(reponse);
		
		decisionService.execute(null, "Ruleflow");
		
		ServiceResponse<Void> response3 = client.disposeContainer("test100");
		System.out.println(response3);
		Assert.assertEquals(ResponseType.SUCCESS, response3.getType());
		

	}

}
