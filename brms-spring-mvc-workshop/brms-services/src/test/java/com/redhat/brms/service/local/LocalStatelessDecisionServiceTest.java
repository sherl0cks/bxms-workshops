package com.redhat.brms.service.local;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.service.api.StatelessDecisionService;

@ActiveProfiles( profiles = { "test-local" } )
@ContextConfiguration( locations = { "classpath:kie-context.xml" } )
public class LocalStatelessDecisionServiceTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private StatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}
	
	@Test
	public void shouldLoadRulesAndCreateAnAuditLog() {
		decisionService.upgradeRulesToVersion("com.redhat.workshops", "business-rules", "1.1");
		Assert.assertNotNull(decisionService);
		decisionService.execute(null, "Ruleflow");
	}

}
