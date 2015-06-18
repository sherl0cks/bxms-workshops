package com.rhc.bpm;

import java.io.File;
import java.io.FilenameFilter;

import org.jbpm.kie.services.impl.KModuleDeploymentUnit;
import org.jbpm.services.api.model.DeploymentUnit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.kie.api.builder.helper.FluentKieModuleDeploymentHelper;
import org.kie.api.builder.helper.KieModuleDeploymentHelper;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieSessionModel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import bitronix.tm.resource.jdbc.PoolingDataSource;

@ContextConfiguration(locations = { "classpath:bpm-services-context.xml" })
public abstract class AbstractBpmServiceTest extends AbstractJUnit4SpringContextTests {

	protected static final String GROUP_ID = "com.redhat.workshops";
	protected static final String ARTIFACT_ID = "test-knowledge";
	protected static final String VERSION = "1.0.0";
	protected static final DeploymentUnit DEPLOYMENT_UNIT = new KModuleDeploymentUnit(GROUP_ID, ARTIFACT_ID, VERSION);
	protected static final String PROCESS_ID = "Ruleflow";
	protected static PoolingDataSource pds;

	@BeforeClass
	public static void generalSetup() {
		FluentKieModuleDeploymentHelper helper1 = KieModuleDeploymentHelper.newFluentInstance();
		createDefaultKieBase(helper1);
		helper1.setGroupId(GROUP_ID).setArtifactId(ARTIFACT_ID).setVersion(VERSION).addResourceFilePath("Rule.drl").addResourceFilePath("Ruleflow.bpmn")
				.createKieJarAndDeployToMaven();

		System.setProperty("java.naming.factory.initial", "bitronix.tm.jndi.BitronixInitialContextFactory");
		pds = setupPoolingDataSource();
	}

	@Before
	public void setup() {
		cleanupSingletonSessionId();

	}

	@AfterClass
	public static void generalCleanup() {
		System.clearProperty("java.naming.factory.initial");
		if (pds != null) {
			pds.close();
		}
	}

	protected static PoolingDataSource setupPoolingDataSource() {
		PoolingDataSource pds = new PoolingDataSource();
		pds.setUniqueName("jdbc/jbpm");
		pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
		pds.setMaxPoolSize(50);
		pds.setAllowLocalTransactions(true);
		pds.getDriverProperties().put("user", "sa");
		pds.getDriverProperties().put("password", "");
		pds.getDriverProperties().put("url", "jdbc:h2:mem:jbpm-db;MVCC=true");
		pds.getDriverProperties().put("driverClassName", "org.h2.Driver");
		pds.init();
		return pds;
	}

	protected static void cleanupSingletonSessionId() {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		if (tempDir.exists()) {

			String[] jbpmSerFiles = tempDir.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {

					return name.endsWith("-jbpmSessionId.ser");
				}
			});
			for (String file : jbpmSerFiles) {

				new File(tempDir, file).delete();
			}
		}
	}

	protected static void createDefaultKieBase(FluentKieModuleDeploymentHelper helper) {
		KieBaseModel kieBaseModel = helper.getKieModuleModel().newKieBaseModel("defaultKieBase").addPackage("*").setDefault(true);
		kieBaseModel.newKieSessionModel("defaultKieSession").setDefault(true);
		kieBaseModel.newKieSessionModel("defaultStatelessKieSession").setType(KieSessionModel.KieSessionType.STATELESS).setDefault(true);
	}
}
