package com.redhat.brms;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.service.api.StatelessDecisionService;

@ActiveProfiles(profiles = { "test-local" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class LocalPremiumDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private StatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	@Test
	public void shouldLoadWorkshopRulesAndExecuteLocally() {
		Collection<Object> facts = new ArrayList<Object>();

		Driver driver = new Driver();
		driver.setAge(30);
		facts.add(driver);

		Premium premium = new Premium();
		facts.add(premium);

		decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "1.0-SNAPSHOT");
		PremiumResponse response = decisionService.execute(facts, "InsurancePremiumRuleFlow", PremiumResponse.class);

		Assert.assertEquals(1, response.getPremiums().size());
	}

}
