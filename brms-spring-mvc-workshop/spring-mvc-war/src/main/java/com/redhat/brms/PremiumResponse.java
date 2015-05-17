package com.redhat.brms;

import java.util.Collection;

import com.redhat.brms.service.common.KieQuery;

public class PremiumResponse {

	@KieQuery(binding = "$premium", queryName = "Get All Premiums")
	private Collection<Premium> premiums;

	public Collection<Premium> getPremiums() {
		return premiums;
	}

	public void setPremiums(Collection<Premium> premiums) {
		this.premiums = premiums;
	}

}
