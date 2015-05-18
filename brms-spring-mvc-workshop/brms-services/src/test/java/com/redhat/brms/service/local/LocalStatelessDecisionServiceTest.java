package com.redhat.brms.service.local;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.Driver;
import com.redhat.brms.Premium;
import com.redhat.brms.Vehicle;
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

	@Test
	public void shouldLoadWorkshopRulesAndExecuteLocally() {
		Collection<Object> facts = new ArrayList<Object>();

		decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "2.0");
		decisionService.execute(facts, "InsurancePremiumRuleFlow");

		decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "2.1");
		decisionService.execute(facts, "InsurancePremiumRuleFlow");
	}

}
