package com.example.demo.models;

public class LocationStats {
	
	private String country;
	private String state;
	private int latestTotalCases;
	private int change;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
	@Override
	public String toString() {
		return "LocationStats [country=" + country + ", state=" + state + ", latestTotalCases=" + latestTotalCases
				+ ", change=" + change + "]";
	}
	
	
	
}
