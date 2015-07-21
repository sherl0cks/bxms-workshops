package com.redhat.approval;

import org.junit.Assert;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ApprovalKnowledgeTest {

	@Test
	public void shouldBuildAndRunKnowledge() throws InterruptedException {
		KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
		Assert.assertNotNull(kieContainer);
		KieSession session = kieContainer.newKieSession();
		Assert.assertNotNull(session);
		session.startProcess("test");
		Thread.sleep(7000l);
		session.dispose();
	}
}
