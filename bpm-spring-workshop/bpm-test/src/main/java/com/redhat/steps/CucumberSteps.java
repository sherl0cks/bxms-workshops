package com.redhat.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CucumberSteps {

	@Given("^I have something$")
	public void i_have_something() throws Throwable {
		System.out.println( "I have something" );
	}

	@When("^I do something$")
	public void i_do_something() throws Throwable {
		System.out.println( "I do something" );
	}

	@Then("^I print something$")
	public void i_print_something() throws Throwable {
		System.out.println( "I print something" );
	}

}
