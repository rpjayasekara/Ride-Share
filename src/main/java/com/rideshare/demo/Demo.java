package com.rideshare.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rideshare.datamodels.LocationNode;
import com.rideshare.datamodels.Ride;
import com.rideshare.datamodels.ShareRequest;
import com.rideshare.datamodels.ShareRequestResponse;
import com.rideshare.services.MapBuilder;
import com.rideshare.services.RequestMapper;
import com.rideshare.services.RouteFinder;




@RestController
@RequestMapping("/test")
public class Demo {

	@Autowired 
	RouteFinder finder;
	@Autowired
	private RequestMapper mapper;
	
	
	@GetMapping("/hello")
	public String sayHello() throws IOException {
		
		DijkstraShortestPath<LocationNode, DefaultWeightedEdge> algo = finder.getPathfinder();
		Graph<LocationNode, DefaultWeightedEdge> map = finder.getGraph();
		Set<LocationNode> nodes = map.vertexSet();
        List<LocationNode> nodeList = new ArrayList<>(nodes);
        GraphPath<LocationNode, DefaultWeightedEdge> path = algo.getPath(nodeList.get(1), nodeList.get(2));
        System.out.println(path.getVertexList());
		return "sdasd";

	}
	
	@PostMapping("/a")
	public List<ShareRequestResponse> setShareRequest(@RequestBody ShareRequest request) {
		System.out.print(request);
		List<ShareRequestResponse> rides = mapper.matchRideRequests(request);
		return rides;
	}
	
}