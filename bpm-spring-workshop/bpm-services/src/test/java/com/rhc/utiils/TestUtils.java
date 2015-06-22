package com.rhc.utiils;

import java.io.File;
import java.io.FilenameFilter;

import org.kie.api.builder.helper.FluentKieModuleDeploymentHelper;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieSessionModel;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class TestUtils {

	private static PoolingDataSource pds;

	public static void setupPoolingDataSource() {
		if (pds == null) {
			System.setProperty("java.naming.factory.initial", "bitronix.tm.jndi.BitronixInitialContextFactory");

			pds = new PoolingDataSource();
			pds.setUniqueName("jdbc/jbpm");
			pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
			pds.setMaxPoolSize(50);
			pds.setAllowLocalTransactions(true);
			pds.getDriverProperties().put("user", "sa");
			pds.getDriverProperties().put("password", "");
			pds.getDriverProperties().put("url", "jdbc:h2:mem:jbpm-db;MVCC=true");
			pds.getDriverProperties().put("driverClassName", "org.h2.Driver");
			pds.init();
		}
	}

	public static void cleanupSingletonSessionId() {
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

	public static void createDefaultKieBase(FluentKieModuleDeploymentHelper helper) {
		KieBaseModel kieBaseModel = helper.getKieModuleModel().newKieBaseModel("defaultKieBase").addPackage("*")
				.setDefault(true);
		kieBaseModel.newKieSessionModel("defaultKieSession").setDefault(true);
		kieBaseModel.newKieSessionModel("defaultStatelessKieSession").setType(KieSessionModel.KieSessionType.STATELESS)
				.setDefault(true);
	}

}
