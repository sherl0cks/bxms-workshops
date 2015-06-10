package com.rhc.services;

import javax.jws.WebService;

import org.apache.hello_world_soap_http.PingMeFault;

@WebService(wsdlLocation = "wsdl/HelloWorld.wsdl", serviceName = "SOAPService", portName = "SoapPort", name = "Greeter", targetNamespace = "http://apache.org/hello_world_soap_http")
public class GreeterService implements org.apache.hello_world_soap_http.Greeter {

	@Override
	public void pingMe() throws PingMeFault {
		// TODO Auto-generated method stub

	}

	@Override
	public String greetMe(String requestType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void greetMeOneWay(String requestType) {
		// TODO Auto-generated method stub

	}

	@Override
	public String sayHi() {
		return Long.toString(System.currentTimeMillis());
	}

}
