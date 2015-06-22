package com.rh;

import org.apache.camel.Exchange;

public class MockBean {

	public String iAmAlive() {
		return "I am Alive";
	}

	public String getEmployeeInfo() {
		return "{\"managerId\":\"msmith\"}";
	}

	public void createRequest(Exchange exchange) {
		exchange.getIn().getHeaders().put(Exchange.HTTP_RESPONSE_CODE, 201);
	}
}
