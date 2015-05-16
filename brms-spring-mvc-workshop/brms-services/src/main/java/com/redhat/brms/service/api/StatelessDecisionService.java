package com.redhat.brms.service.api;

import java.util.Collection;

public interface StatelessDecisionService {

	public <Response> Response execute(Collection<Object> facts, String processId, Class<Response> responseClazz);

	public <Response> Response execute(Collection<Object> facts, String processId);

	public <Response> Response execute(Collection<Object> facts, Class<Response> responseClazz);

	public <Response> Response execute(Collection<Object> facts);

	public boolean createOrUpgradeRulesWithVersion(String group, String artifact, String version);

}