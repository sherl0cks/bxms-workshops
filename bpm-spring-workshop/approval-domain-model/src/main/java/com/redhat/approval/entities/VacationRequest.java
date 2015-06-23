package com.redhat.approval.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.kie.api.definition.type.PropertyReactive;

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
	private String comment;

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
		return Days.daysBetween(startDate, endDate).getDays();
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comments) {
		this.comment = comments;
	}
	
	/*
	 * This is a hack for expediency
	 */
	public String parseResponse(String json){
		
		return json.substring(14, 20);
	}

	public Map<String, Object> toProcessDataMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("vacationRequest", this);
		if ( this.getEmployee().getManager() != null ){
			map.put("managerId", this.getEmployee().getManager().getUserId());
		}
		map.put("employeeId", this.getEmployee().getUserId());
		return map;
	}

	@Override
	public String toString() {
		return "VacationRequest [employee=" + employee + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", comment=" + comment + "]";
	}

}
