package com.rhc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.redhat.bpm.approval.VacationRequestService;

@Controller
@RequestMapping("/")
public class HelloController {

	@Autowired
	private VacationRequestService service;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	@ResponseBody
	@RequestMapping( value="/hello", method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		
		return "hello world";
	}
	
	
	@ResponseBody
	@RequestMapping( value="/deploy", method = RequestMethod.GET)
	public String deploy() {
		
		service.startTestProcess();
		LOGGER.info("deployed and run");
		return "I'm deploying";
	}

}
