package com.redhat.brms;

public class Vehicle {
	private String vin;
	private String model;
	private int year;
	private String make;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	@Override
	public String toString() {
		return "Vehicle [vin=" + vin + ", model=" + model + ", year=" + year + ", make=" + make + "]";
	}

}
