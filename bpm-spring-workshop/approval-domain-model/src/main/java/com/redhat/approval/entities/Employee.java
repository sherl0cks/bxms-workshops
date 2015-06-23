package com.redhat.approval.entities;

import java.io.Serializable;

import org.kie.api.definition.type.PropertyReactive;

@PropertyReactive
public class Employee implements Serializable {

	/**
	 * generated serial UID
	 */
	private static final long serialVersionUID = 2616643207616041365L;

	private String userId;
	private String firstName;
	private String lastName;
	private Employee manager;

	public Employee() {
	}

	public Employee(String userId, String firstName, String lastName, Employee manager) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.manager = manager;
	}
	
	public Employee(String userId, String firstName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "Employee [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", manager=" + manager + "]";
	}
	
	

}
