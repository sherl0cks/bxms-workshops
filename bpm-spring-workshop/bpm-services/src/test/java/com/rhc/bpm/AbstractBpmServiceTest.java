package com.rhc.bpm;

import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.UserTaskService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import bitronix.tm.resource.jdbc.PoolingDataSource;

import com.rhc.utiils.TestUtils;

@ContextConfiguration(locations = { "classpath:bpm-services-context.xml" })
public abstract class AbstractBpmServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	protected ProcessService processService;
	@Autowired
	protected RuntimeDataService runtimeDataService;
	@Autowired
	protected DeploymentService deploymentService;
	@Autowired
	protected UserTaskService userTaskService;

	protected static PoolingDataSource pds;

	@BeforeClass
	public static void generalSetup() {
		TestUtils.setupPoolingDataSource();
	}

	@Before
	public void setup() {
		TestUtils.cleanupSingletonSessionId();

	}

}
