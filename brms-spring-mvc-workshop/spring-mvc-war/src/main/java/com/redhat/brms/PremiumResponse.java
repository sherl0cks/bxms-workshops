package com.redhat.brms;

import java.util.Collection;

import com.redhat.brms.service.common.KieQuery;

public class PremiumResponse {

	@KieQuery(binding = "$premium", queryName = "Get All Premiums")
	private Collection<Premium> premiums;

	
	public Premium getPremium(){
		for (Premium p : premiums ){
			return p;
		}
		return null;
	}
	
	public Collection<Premium> getPremiums() {
		return premiums;
	}

	public void setPremiums(Collection<Premium> premiums) {
		this.premiums = premiums;
	}

	@Override
	public String toString() {
		return "PremiumResponse [premiums=" + premiums + "]";
	}

}
