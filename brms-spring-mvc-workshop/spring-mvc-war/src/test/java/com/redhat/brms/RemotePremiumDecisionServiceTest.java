package com.redhat.brms;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.service.api.StatelessDecisionService;

@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class RemotePremiumDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource( name = "remoteDecisionService")
	private StatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	/*
	 * This test is ignored because it needs a network and we don't know if you local environment can access that network
	 */
	@Ignore
	@Test
	public void shouldLoadWorkshopRulesAndExecuteLocally() {
		Collection<Object> facts = new ArrayList<Object>();

		Driver driver = new Driver();
		driver.setAge(30);
		facts.add(driver);

		Vehicle vehicle = new Vehicle();
		vehicle.setMake("BMW");
		facts.add(vehicle);

		decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "2.1");
		PremiumResponse response = decisionService.execute(facts, "InsurancePremiumRuleFlow", PremiumResponse.class);
	
		Assert.assertEquals(1, response.getPremiums().size());
	}

}
