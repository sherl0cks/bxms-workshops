package com.redhat.bpm.identity;

import java.util.ArrayList;
import java.util.List;

import org.kie.internal.identity.IdentityProvider;

public class DefaultIdentityProvider implements IdentityProvider {

	@Override
	public String getName() {
		return "default";
	}

	@Override
	public List<String> getRoles() {
		List<String> list = new ArrayList<String>();
		list.add("default");
		return list;
	}

	@Override
	public boolean hasRole(String role) {
		return true;
	}

}
