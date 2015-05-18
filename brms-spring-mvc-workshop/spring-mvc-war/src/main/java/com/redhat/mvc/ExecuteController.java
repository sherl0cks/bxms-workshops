package com.redhat.mvc;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.redhat.brms.Driver;
import com.redhat.brms.PremiumResponse;
import com.redhat.brms.Vehicle;
import com.redhat.brms.service.api.StatelessDecisionService;

@Controller
@RequestMapping("/execute")
public class ExecuteController {

	private static final String RULE_VERSION = "2.1";
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteController.class);

	@Resource(name = "localDecisionService")
	private StatelessDecisionService localDecisionService;
	@Resource(name = "remoteDecisionService")
	private StatelessDecisionService remoteDecisionService;
	private boolean serviceInit;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		System.out.println("execute");
		return "execute";
	}

	@RequestMapping(value = "/premium/{type}", method = RequestMethod.GET)
	public String printFoo(@PathVariable String type, @ModelAttribute Driver driver, @ModelAttribute Vehicle vehicle, ModelMap model) {
		if (serviceInit == false) {
			localDecisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", RULE_VERSION);
			remoteDecisionService.createOrUpgradeRulesWithVersion("com.redhat.workshops", "business-rules", RULE_VERSION);
			serviceInit = true;
		}

		Collection<Object> facts = new ArrayList<Object>();
		facts.add(driver);
		facts.add(vehicle);

		PremiumResponse response = null;
		if (type.equals("local")) {
			response = localDecisionService.execute(facts, "InsurancePremiumRuleFlow", PremiumResponse.class);
		} else if (type.equals("remote")) {
			response = remoteDecisionService.execute(facts, "InsurancePremiumRuleFlow", PremiumResponse.class);
		} else {
			LOGGER.error(String.format("No decision service of type %s", type));
		}
		if (response != null && response.getPremium() != null) {
			model.put("premium", response.getPremium());
			model.put("driver", driver);
			model.put("vehicle", vehicle);
		}
		LOGGER.info(model.toString());

		return "premium";
	}
}
