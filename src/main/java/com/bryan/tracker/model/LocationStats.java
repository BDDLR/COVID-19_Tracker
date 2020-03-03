package com.bryan.tracker.model;

public class LocationStats {
	private String state;
	private String country;
	private int latestTotal;
	private int increaseFromYesterday;
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatestTotal() {
		return latestTotal;
	}

	public void setLatestTotal(int latestTotal) {
		this.latestTotal = latestTotal;
	}

	public int getIncreaseFromYesterday() {
		return increaseFromYesterday;
	}

	public void setIncreaseFromYesterday(int increaseFromYesterday) {
		this.increaseFromYesterday = increaseFromYesterday;
	}
	
	

}
