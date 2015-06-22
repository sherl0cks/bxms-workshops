package com.rhc.bpm.runner;

import java.io.IOException;

import org.junit.runners.model.InitializationError;

import bitronix.tm.resource.jdbc.PoolingDataSource;

import com.rhc.utiils.TestUtils;

import cucumber.api.junit.Cucumber;

public class CucumberProfileRunner extends Cucumber {

	private static PoolingDataSource pds;

	static {
		// System.getProperties().setProperty("spring.active.profiles", "test");
		TestUtils.setupPoolingDataSource();

	}

	public CucumberProfileRunner(Class clazz) throws InitializationError, IOException {
		super(clazz);
		// TODO Auto-generated constructor stub
	}

}
