package com.redhat.brms;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import org.junit.Test;

public class PremiumRulesTest {

	static KieBase kbase;
	static KieSession ksession;
	static KieRuntimeLogger klogger;

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@BeforeClass
	public static void setupKsession() {
		try {
			ksession = readKnowledgeBase();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@AfterClass
	public static void closeKsession() {
		try {
			// closing resources
			ksession.dispose();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void drlAuthoringTest() {

		Driver driver = new Driver();
		driver.setAge(30);


		Premium premium = new Premium();


		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle premiumFH = ksession.insert(premium);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(premiumFH);

		assertEquals("Price is 300", new Integer(300), premium.getAmount());
	}

	@Test
	public void dslAuthoringTest() {

		// now create some test data
		Driver driver = new Driver();
		driver.setAge(20);


		Premium premium = new Premium();

		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle premiumFH = ksession.insert(premium);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(premiumFH);

		assertEquals("Price is 700", new Integer(700), premium.getAmount());

	}

	@Test
	public void decisionTableTest() {

		// now create some test data
		Driver driver = new Driver();
		driver.setAge(30);


		Premium premium = new Premium();


		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle premiumFH = ksession.insert(premium);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(premiumFH);

		assertEquals("Price is 120", new Integer(120), premium.getAmount());
	}

	@Test
	public void flowTest() {

		// now create some test data
		Driver driver = new Driver();


		Premium premium = new Premium();


		// insert objects into working memory
		FactHandle driverFH = ksession.insert(driver);
		FactHandle premiumFH = ksession.insert(premium);
		ksession.fireAllRules();
		ksession.delete(driverFH);
		ksession.delete(premiumFH);

		assertEquals("Price is 450", new Integer(450), premium.getAmount());
	}

	private static KieSession readKnowledgeBase() throws Exception {

		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession();

		return kSession;
	}

}
