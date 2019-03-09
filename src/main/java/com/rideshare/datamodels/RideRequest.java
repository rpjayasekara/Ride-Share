package com.rideshare.datamodels;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

public class RideRequest 
{
	private int origin;
	private int destination;
	private GraphPath<LocationNode, DefaultWeightedEdge> path;
	private int id;
	public RideRequest() {
		
	}
	public RideRequest(int origin, int destinatio, int id) {
		this.origin=origin;
		this.destination=destinatio;
		this.id=id;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public GraphPath<LocationNode, DefaultWeightedEdge> getPath() {
		return path;
	}
	public void setPath(GraphPath<LocationNode, DefaultWeightedEdge> path) {
		this.path = path;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
