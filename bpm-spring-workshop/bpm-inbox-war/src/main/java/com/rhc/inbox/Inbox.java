package com.rhc.inbox;

import java.util.ArrayList;
import java.util.Collection;

public class Inbox {

	private Long id;

	private Collection<Task> tasks;

	public Inbox() {
	}

	public Inbox(Long id) {
		this.id = id;
	}

	public Collection<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Collection<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task task) {
		if (tasks == null) {
			this.tasks = new ArrayList<Task>();
		}
		this.tasks.add(task);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
