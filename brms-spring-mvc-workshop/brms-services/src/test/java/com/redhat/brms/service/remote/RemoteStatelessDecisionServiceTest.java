package com.redhat.brms.service.remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.drools.core.command.runtime.process.StartProcessCommand;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerStatus;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.client.KieServicesClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.redhat.brms.Driver;
import com.redhat.brms.Vehicle;

@ActiveProfiles(profiles = { "test-remote" })
@ContextConfiguration(locations = { "classpath:kie-context.xml" })
public class RemoteStatelessDecisionServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource(name = "remoteDecisionService")
	private RemoteStatelessDecisionService decisionService;

	@Test
	public void shouldAutowireDecisionService() {
		Assert.assertNotNull(decisionService);
	}

	/*
	 * This test is ignored because it needs a network and we don't know if you
	 * local environment can access that network
	 */
	@Ignore
	@Test
	public void shouldCreateContainerAndUpgradeIt() {
		Assert.assertNotNull(decisionService);
		KieServicesClient client = decisionService.getClient();

		client.createContainer("test", new KieContainerResource("test", new ReleaseId("com.redhat.workshops", "business-rules", "2.1"), KieContainerStatus.STARTED));

		boolean response = decisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "2.1");
		Assert.assertTrue(response);

		decisionService.execute(null);

		ServiceResponse<Void> response3 = client.disposeContainer("test");
		System.out.println(response3);
		Assert.assertEquals(ResponseType.SUCCESS, response3.getType());
	}

	@Test
	public void shouldRecreateBug1221862Variant2() {
		// Given
		Collection<Object> facts = new ArrayList<Object>();

		Driver driver = new Driver();
		driver.setAge(30);
		facts.add(driver);

		Vehicle vehicle = new Vehicle();
		vehicle.setMake("BMW");
		facts.add(vehicle);

		// When
		String payload = createRequest(facts, "InsurancePremiumRuleFlow");
		System.err.println( payload );
		ServiceResponse<String> response = decisionService.getClient().executeCommands("test", payload);

		// Then
		Assert.assertEquals(ResponseType.FAILURE, response.getType());

	}

	private String createRequest(Collection<Object> facts, String processId) {

		KieCommands commandFactory = KieServices.Factory.get().getCommands();
		;

		List<Command<?>> commands = new ArrayList<Command<?>>();

		if (facts != null) {
			commands.add(commandFactory.newInsertElements(facts));
		}
		if (processId != null && !processId.isEmpty()) {
			StartProcessCommand c = (StartProcessCommand) commandFactory.newStartProcess(processId);
			c.setOutIdentifier("identifier");
			commands.add(c);
		}

		commands.add(commandFactory.newFireAllRules());

		String payload = BatchExecutionHelper.newXStreamMarshaller().toXML(commandFactory.newBatchExecution(commands, "defaultStatelessKieSession"));
		return payload;
	}

}
