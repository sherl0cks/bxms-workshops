package com.rhc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class HelloWorld {

	@Autowired
	private Foo foo;

	@Produce(uri = "direct:out")
	private ProducerTemplate template;

	@GET
	public String getRooms() {
		if (template == null) {
			System.err.println("error");
		} else {
			String response = template.requestBody("direct:in", "fooooo", String.class);
			System.err.println(response);

		}
		System.err.println("test3");
		return "hello world";

	}

	public Foo getFoo() {
		return foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	public ProducerTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ProducerTemplate template) {
		this.template = template;
	}

}
