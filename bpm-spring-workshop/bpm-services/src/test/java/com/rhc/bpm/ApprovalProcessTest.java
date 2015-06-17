package com.rhc.bpm;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import mockit.Mocked;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;

@Ignore
public class ApprovalProcessTest {
	
	@Mocked
	private EntityManagerFactory emf;

	@Test
	public void test() {
		
		RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder().entityManagerFactory(Persistence.createEntityManagerFactory("org.jbpm.domain"))
				.addAsset(ResourceFactory.newClassPathResource("Ruleflow.bpmn"), ResourceType.BPMN2).addAsset(ResourceFactory.newClassPathResource("Rule.drl"), ResourceType.DRL).get();


		// next create RuntimeManager - in this case singleton strategy is
		// chosen

		RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);

		// then get RuntimeEngine out of manager - using empty context as
		// singleton does not keep track

		// of runtime engine as there is only one

		RuntimeEngine runtime = manager.getRuntimeEngine(EmptyContext.get());

		KieSession session = runtime.getKieSession();
		ProcessInstance instance = session.startProcess("com.redhat.workshops.VacationApproval");
		Assert.assertNotNull(instance);
		session.fireAllRules();
	}

}
