package com.redhat.brms.service.local;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.Driver;
import com.redhat.brms.Premium;
import com.redhat.brms.service.api.StatelessDecisionService;

@ActiveProfiles(profiles = { "test-local" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class LocalStatelessDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private StatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	@Test
	public void shouldLoadRulesAndCreateAnAuditLog() {
		decisionService.createOrUpgradeRulesWithVersion("com.rhc", "basic-business-rules", "1.0");
		Assert.assertNotNull(decisionService);
		decisionService.execute(null, "Ruleflow");
		decisionService.createOrUpgradeRulesWithVersion("com.rhc", "basic-business-rules", "1.1");
		decisionService.execute(null, "Ruleflow");
	}

	@Ignore
	@Test
	public void shouldLoadWorkshopRulesAndExecuteLocally() {
		Collection<Object> facts = new ArrayList<Object>();

		Driver driver = new Driver();
		driver.setAge(30);
		facts.add(driver);

		Premium premium = new Premium();
		facts.add(premium);

		decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "1.0-SNAPSHOT");
		decisionService.execute(facts, "InsurancePremiumRuleFlow");

		// assertEquals("Price is 300", new Integer(300), premium.getAmount());
	}

}
