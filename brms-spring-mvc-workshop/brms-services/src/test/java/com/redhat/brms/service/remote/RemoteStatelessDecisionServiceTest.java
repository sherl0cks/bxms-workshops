package com.redhat.brms.service.remote;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ActiveProfiles(profiles = { "test-remote" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class RemoteStatelessDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "remoteDecisionService")
	private RemoteStatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	@Test
	public void shouldCreateContainerAndUpgradeIt() {
		Assert.assertNotNull(decisionService);
		KieServicesClient client = decisionService.getClient();
		decisionService.setContainerId("test");

		boolean response = decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "2.1");
		Assert.assertTrue(response);

		decisionService.execute(null);

		ServiceResponse<Void> response3 = client.disposeContainer("test");
		System.out.println(response3);
		Assert.assertEquals(ResponseType.SUCCESS, response3.getType());
	}

}
