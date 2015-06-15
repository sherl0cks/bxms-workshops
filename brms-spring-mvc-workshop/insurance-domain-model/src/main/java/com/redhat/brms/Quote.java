package com.redhat.brms;

import java.math.BigDecimal;

public class Quote {
private String eligibility;
private BigDecimal totalPremium;
public String getEligibility() {
	return eligibility;
}
public void setEligibility(String eligibility) {
	this.eligibility = eligibility;
}
public BigDecimal getTotalPremium() {
	return totalPremium;
}
public void setTotalPremium(BigDecimal totalPremium) {
	this.totalPremium = totalPremium;
}

}
