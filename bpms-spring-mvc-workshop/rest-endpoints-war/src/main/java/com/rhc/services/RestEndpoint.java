package com.rhc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.hello_world_soap_http.Greeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class RestEndpoint {

	@Produce
	private ProducerTemplate template;

	@Autowired
	private Greeter greeter;

	@GET
	public String getRooms() {
		return (String) template.requestBody("direct:in", "foo");

	}

	public ProducerTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ProducerTemplate template) {
		this.template = template;
	}

}
