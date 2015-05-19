package com.redhat.mvc;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.redhat.brms.service.api.StatelessDecisionService;

@Controller
@RequestMapping("/deploy")
public class DeployController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeployController.class);

	@Resource(name = "localDecisionService")
	private StatelessDecisionService localDecisionService;
	@Resource(name = "remoteDecisionService")
	private StatelessDecisionService remoteDecisionService;
	
	private boolean serviceInit;

	@RequestMapping(method = RequestMethod.GET)
	public String printHello(ModelMap model) {
		model.put("releaseidLocal", localDecisionService.getCurrentVersion());
		model.put("releaseidRemote", remoteDecisionService.getCurrentVersion());
		return "deploy";
	}

	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
	public String deployRules(@PathVariable String type, @RequestParam String group, @RequestParam String artifact, @RequestParam String version, ModelMap model) {

		boolean success = false;
		if (type.equals("local")){
			success = localDecisionService.createOrUpgradeRulesWithVersion(group, artifact, version);
		} else{
			success = remoteDecisionService.createOrUpgradeRulesWithVersion(group, artifact, version);
		}
		
		String releaseid = "";
		if ( success){
			releaseid = String.format("successfully upgraded to %s %s %s", group, artifact, version);
		} else {
			releaseid = "deployment failed, check your logs";
		}
		LOGGER.info(releaseid);
		model.put("releaseid", releaseid);

		return "ruledeploy";
	}
}
