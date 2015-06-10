package com.rhc.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/hello")
public class HelloWorld {

	@Autowired
	private Foo foo;

	@GET
	public String getRooms() {
		if (foo == null) {
			System.err.println("it is null");
		} else {
			System.err.println(foo.getName());
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

}
