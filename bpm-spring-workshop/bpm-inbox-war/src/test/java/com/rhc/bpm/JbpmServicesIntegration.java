package com.rhc.bpm;

import java.io.File;

import javax.persistence.Persistence;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.internal.runtime.manager.context.EmptyContext;

@RunWith(Arquillian.class)
public class JbpmServicesIntegration {

	@Deployment
	public static Archive<?> createTestArchive() {

		return ShrinkWrap.create(ZipImporter.class, "bpm-inbox-war.war").importFrom(new File("target/bpm-inbox-war.war")).as(WebArchive.class);
	}

	@Test
	public void shouldCreateLocalRuntimeWithoutErrors() {
		System.err.println("hello world");

		RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder("com.redhat.workshops", "approval-knowledge", "1.0.0-SNAPSHOT")
				.entityManagerFactory(Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa")).get();


		// next create RuntimeManager - in this case singleton strategy is
		// chosen

		RuntimeManager manager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(environment);

		// then get RuntimeEngine out of manager - using empty context as
		// singleton does not keep track

		// of runtime engine as there is only one

		RuntimeEngine runtime = manager.getRuntimeEngine(EmptyContext.get());

		// get KieSession from runtime runtimeEngine - already initialized
		// with
		// all handlers, listeners, etc that were configured

		// on the environment

		KieSession ksession = runtime.getKieSession();

		KieRuntimeLogger auditLogger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "jbpm-audit");
		ksession.startProcess("com.redhat.workshops.VacationApproval");
		ksession.fireAllRules();
		ksession.dispose();
		auditLogger.close();

		// add invocations to the process engine here,

		// e.g. ksession.startProcess(processId);

		// and last dispose the runtime engine

		manager.disposeRuntimeEngine(runtime);

	}

}
