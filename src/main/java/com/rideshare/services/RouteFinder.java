package com.rideshare.services;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rideshare.datamodels.LocationNode;


public class RouteFinder
{
	private DijkstraShortestPath pathfinder;
	private Graph<LocationNode, DefaultWeightedEdge> graph;
	@Autowired
	private MapBuilder mapBuilder;
	public RouteFinder(Graph<LocationNode, DefaultWeightedEdge> map) {
		pathfinder = new DijkstraShortestPath(map);
		graph=map;
	}
	
	public DijkstraShortestPath getPathfinder() {
		return pathfinder;
	}
	public void setPathfinder(DijkstraShortestPath pathfinder) {
		this.pathfinder = pathfinder;
	}

	public Graph<LocationNode, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	public void setGraph(Graph<LocationNode, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}
}
