package com.rhc.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.github.rjeschke.txtmark.Processor;

@Path("/")
public class RootEndpoint {

	@GET
	@Produces("text/HTML")
	public String test() throws IOException {

		String result = Processor.process(this.getClass().getClassLoader()
				.getResourceAsStream("test.md"));

		return result;
	}
}
