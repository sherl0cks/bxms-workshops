package com.redhat.approval.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import org.kie.api.definition.type.PropertyReactive;
import org.omg.CORBA.Request;

import com.redhat.approval.value.RequestStatus;

@PropertyReactive
public class VacationRequest implements Serializable {

	/**
	 * generated serial UID
	 */
	private static final long serialVersionUID = -8354225930529331655L;

	private Employee employee;
	private LocalDate startDate;
	private LocalDate endDate;
	private RequestStatus status = RequestStatus.CREATED;

	public VacationRequest() {
	}

	public VacationRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int lengthInDays() {
		return Period.between(startDate, endDate).getDays();
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "VacationRequest [employee=" + employee + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}

}
