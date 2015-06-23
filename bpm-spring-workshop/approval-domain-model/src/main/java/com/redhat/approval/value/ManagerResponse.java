package com.redhat.approval.value;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ManagerResponse {

	@XmlElement
	public String managerId;

}
