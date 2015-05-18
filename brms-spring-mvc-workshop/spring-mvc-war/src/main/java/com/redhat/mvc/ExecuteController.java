package com.redhat.mvc;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.redhat.brms.Driver;
import com.redhat.brms.PremiumResponse;
import com.redhat.brms.Vehicle;
import com.redhat.brms.service.api.StatelessDecisionService;

@Controller
@RequestMapping("/execute")
public class ExecuteController {

	@Autowired
	private StatelessDecisionService localDecisionService;
	private boolean serviceInit;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		System.out.println("execute");
		return "execute";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postRequest(@RequestBody String body, @ModelAttribute Driver driver, @ModelAttribute Vehicle vehicle, ModelMap model) {

		if (serviceInit == false) {
			localDecisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", "1.0-SNAPSHOT");
			serviceInit = true;
		}

		Collection<Object> facts = new ArrayList<Object>();
		facts.add(driver);
		facts.add(vehicle);

		PremiumResponse response = localDecisionService.execute(facts, "InsurancePremiumRuleFlow", PremiumResponse.class);
		if (response.getPremium() != null) {
			model.put("premium", response.getPremium());
		}
		model.put("driver", driver);
		model.put("vehicle", vehicle);
		System.out.println(model);

		return "deploy";
	}
}
