package com.rhc.inbox;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.task.model.TaskSummary;

public class Tasks {

	private Long current = 1l;
	private Long rowCount = 0l;
	private List<Task> rows = new ArrayList<>();
	private Long total = 0l;

	public Tasks(List<TaskSummary> tasks) {
		for (TaskSummary summary : tasks) {
			rows.add(new Task(summary.getId(), summary.getName(), summary.getStatus().toString()));
			rowCount++;
			total++;
		}
	}

	public Long getCurrent() {
		return current;
	}

	public void setCurrent(Long current) {
		this.current = current;
	}

	public Long getRowCount() {
		return rowCount;
	}

	public void setRowCount(Long rowCount) {
		this.rowCount = rowCount;
	}

	public List<Task> getRows() {
		return rows;
	}

	public void setRows(List<Task> rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
