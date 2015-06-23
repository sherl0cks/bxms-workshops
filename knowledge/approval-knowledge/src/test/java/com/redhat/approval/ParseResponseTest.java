package com.redhat.approval;

import org.junit.Assert;
import org.junit.Test;

import com.redhat.approval.entities.VacationRequest;

public class ParseResponseTest {

	private String json = "{\"managerId\":\"msmith\"}";
	
	@Test
	public void test(){
		VacationRequest request = new VacationRequest();
		
		Assert.assertEquals("msmith", request.parseResponse(json));
		
	}
	
}
