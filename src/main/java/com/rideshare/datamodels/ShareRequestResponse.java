package com.rideshare.datamodels;

public class ShareRequestResponse 
{
	int rideoneID;
	int rideTwoID;
	int driverId;
	public ShareRequestResponse(int one, int two, int id) {
		rideoneID=one;
		rideTwoID=two;
		driverId=id;
	}
	public int getRideoneID() {
		return rideoneID;
	}
	public void setRideoneID(int rideoneID) {
		this.rideoneID = rideoneID;
	}
	public int getRideTwoID() {
		return rideTwoID;
	}
	public void setRideTwoID(int rideTwoID) {
		this.rideTwoID = rideTwoID;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
}
