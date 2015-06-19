package com.rhc.test;

import java.time.LocalDate;

import com.redhat.approval.entities.Employee;
import com.redhat.approval.entities.VacationRequest;

public class TestDataUtil {

	private static Employee mary = new Employee("msmith", "Mary", "Smith", null);
	private static Employee tom = new Employee("tbrown", "Thomas", "Brown", mary);
	private static LocalDate startDate = LocalDate.of(2015, 7, 6);
	private static LocalDate endDate = LocalDate.of(2015, 7, 10);
	private static VacationRequest vacationRequest = new VacationRequest(tom, startDate, endDate);

	public static Employee getMary() {
		return mary;
	}

	public static Employee getTom() {
		return tom;
	}

	public static VacationRequest getVacationRequest() {
		return vacationRequest;
	}

	public static LocalDate getStartDate() {
		return startDate;
	}

	public static LocalDate getEndDate() {
		return endDate;
	}

}
