package com.rideshare.datamodels;

import java.util.ArrayList;
import java.util.List;

public class Ride 
{
	private List<RideRequest> requests = new ArrayList<RideRequest>();
	private Driver driver;
	public Ride() {
	}
	public List<RideRequest> getRequests() {
		return requests;
	}
	public void setRequests(List<RideRequest> requests) {
		this.requests = requests;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
