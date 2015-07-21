package com.redhat.approval;

import java.util.HashMap;
import java.util.Map;

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
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put( "comment", "hellooooooO!");
		vars.put( "retryInterval", "1s");
		vars.put( "currentRetry", new Integer(0) );
		vars.put( "maxRetry", new Integer(1) );
		
		session.startProcess("test", vars);
		Thread.sleep(3000l);
		session.dispose();
	}
}
