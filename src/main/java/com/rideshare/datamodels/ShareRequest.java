package com.rideshare.datamodels;

import java.util.ArrayList;
import java.util.List;

public class ShareRequest 
{
	private RideRequest[] requests;
	
	public ShareRequest() {
		
	}

	public RideRequest[] getRequests() {
		return requests;
	}

	public void setRequests(RideRequest[] requests) {
		this.requests = requests;
	}
	
}
