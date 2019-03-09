package com.rideshare.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rideshare.datamodels.ShareRequest;
import com.rideshare.datamodels.ShareRequestResponse;
import com.rideshare.services.RequestMapper;

@RestController
@RequestMapping("api")
public class RideRequestController
{
	@Autowired
	private RequestMapper mapper;
	@PostMapping("/shareride")
	public List<ShareRequestResponse> setShareRequest(@RequestBody ShareRequest request) {
		System.out.print(request);
		List<ShareRequestResponse> rides = mapper.matchRideRequests(request);
		return rides;
	}

}
