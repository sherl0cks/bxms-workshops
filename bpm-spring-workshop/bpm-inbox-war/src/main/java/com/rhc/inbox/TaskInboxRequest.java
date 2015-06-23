package com.rhc.inbox;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TaskInboxRequest {

	@XmlElement(name = "current")
	private Long current;
	@XmlElement(name = "rowCount")
	private Long rowCount;
	@XmlElement(name = "searchPhrase")
	private String searchPhrase;
	@XmlElement(name = "id")
	private String id;

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

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "TaskInboxRequest [current=" + current + ", rowCount=" + rowCount + ", searchPhrase=" + searchPhrase + ", id=" + id + "]";
	}

}
