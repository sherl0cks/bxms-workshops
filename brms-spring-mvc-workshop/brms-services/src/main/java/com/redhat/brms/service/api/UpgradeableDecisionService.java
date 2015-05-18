package com.redhat.brms.service.api;

public interface UpgradeableDecisionService {

	public boolean createOrUpgradeRulesWithVersion(String group, String artifact, String version);
}
