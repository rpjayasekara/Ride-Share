package com.rideshare.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rideshare.datamodels.Driver;
import com.rideshare.datamodels.LocationNode;
import com.rideshare.datamodels.Ride;
import com.rideshare.datamodels.RideRequest;
import com.rideshare.datamodels.ShareRequest;
import com.rideshare.datamodels.ShareRequestResponse;

@Service
public class RequestMapper
{
	@Autowired 
	private RouteFinder finder;
	
	public List<ShareRequestResponse> matchRideRequests(ShareRequest shareRequest) {
		findShortestPath(shareRequest);
		List<GraphPath<LocationNode, DefaultWeightedEdge>> paths = getPathList(shareRequest);
		List<Ride> rides = matchRides(paths, shareRequest);
		Graph<LocationNode, DefaultWeightedEdge> graph = finder.getGraph();
		Set<LocationNode> nodes = graph.vertexSet();
        List<LocationNode> nodeList = new ArrayList<>(nodes);
		Driver one = new Driver(nodeList.get(0), 1);
        Driver two = new Driver(nodeList.get(1), 2);
        List<Driver> drivers = new ArrayList<>();
        drivers.add(one);
        drivers.add(two);
        List<ShareRequestResponse> response = new ArrayList<ShareRequestResponse>();
        for(Ride ride: rides) {
        	if(ride.getRequests().get(1)!=null) {
        	ride.setDriver(findBestDriver(ride.getRequests().get(0).getPath(), ride.getRequests().get(1).getPath(), drivers));
        	response.add(new ShareRequestResponse(ride.getRequests().get(0).getId(), ride.getRequests().get(1).getId(), ride.getDriver().getId()));
        	}else {
        		ride.setDriver(findNearestTaxiDriver(drivers, ride.getRequests().get(0).getPath()));
        		response.add(new ShareRequestResponse(ride.getRequests().get(0).getId(), 0, ride.getDriver().getId()));
        	}
        }
        return response;
		
	}
	
	public LocationNode findNodeById(Set<LocationNode> nodes, int id){
        for(LocationNode node: nodes){
            if(node.getNodeID()==id){
                return node;
            }
        }
        return null;
    }
	
	public void findShortestPath(ShareRequest shareRequest){
		Graph<LocationNode, DefaultWeightedEdge> map = finder.getGraph();
		for(RideRequest rideRequest: shareRequest.getRequests()) {
			rideRequest.setPath(finder.getPathfinder().getPath(findNodeById(map.vertexSet(), rideRequest.getOrigin()), findNodeById(map.vertexSet(), rideRequest.getDestination())));
		}
	}
	
	public RideRequest findRequestByPath(ShareRequest shareRequest, GraphPath<LocationNode, DefaultWeightedEdge> path) {
		RideRequest request = null;
		for(RideRequest rideRequest: shareRequest.getRequests()) {
			if(rideRequest.getPath().equals(path)) {
				request = rideRequest;
				return request;
			}
		}
		return request;
	}
	
	public List<LocationNode> getCommonPath(GraphPath<LocationNode, DefaultWeightedEdge> a, GraphPath<LocationNode, DefaultWeightedEdge> b){
        List<LocationNode> pathOne = a.getVertexList();
        List<LocationNode> pathTwo = b.getVertexList();
        List<LocationNode> common = new ArrayList<>(pathOne);
        common.retainAll(pathTwo);
        return common;
    }
	
	public List<Ride> matchRides(List<GraphPath<LocationNode, DefaultWeightedEdge>> paths, ShareRequest shareRequest){
        List<Integer> a = new ArrayList<>();
        List<Ride> rides = new ArrayList<Ride>();
        List<GraphPath<LocationNode, DefaultWeightedEdge>> matchedPaths = new ArrayList<>();
        for (int i = 0; i<paths.size(); i++){
            if(matchedPaths.contains(paths.get(i))){
                continue;
            }
            GraphPath<LocationNode, DefaultWeightedEdge> matchedPath = null;
//            int b = 0;
            matchedPaths.add(paths.get(i));
//            a.add(i);
            int maxCommonNodes = 0;
            int commonNodes;
            List<GraphPath<LocationNode, DefaultWeightedEdge>> couplePath = new ArrayList<>();
            couplePath.add(paths.get(i));
            System.out.println("from I "+i);
            for (int j = i; j<paths.size(); j++){
                if(i==j){
                    continue;
                }
                if(matchedPaths.contains(paths.get(j))){
                    continue;
                }
                commonNodes = getCommonPath(paths.get(i), paths.get(j)).size();
//                System.out.println(commonNodes);
                if(commonNodes>maxCommonNodes){
                    System.out.println(commonNodes);
                    System.out.println("when "+i+" "+j);
                    maxCommonNodes=commonNodes;
                    matchedPath = paths.get(j);
                }
                System.out.println("from j "+j);
            }

            matchedPaths.add(matchedPath);
//            System.out.println(matchedPaths);
//            a.add(b);
            couplePath.add(matchedPath);
//            System.out.println(a);
            System.out.println(couplePath);
            List<RideRequest> requests = new ArrayList<RideRequest>();
            Ride ride = new Ride();
            for(GraphPath<LocationNode, DefaultWeightedEdge> path: couplePath) {
            	requests.add(findRequestByPath(shareRequest, path));
            }
            ride.setRequests(requests);
            rides.add(ride);
        }
        return rides;
    }
	
	public List<GraphPath<LocationNode, DefaultWeightedEdge>> getPathList(ShareRequest shareRequest){
		List<GraphPath<LocationNode, DefaultWeightedEdge>> paths = new ArrayList<GraphPath<LocationNode,DefaultWeightedEdge>>();
		for(RideRequest request: shareRequest.getRequests()) {
			paths.add(request.getPath());
		}
		return paths;
	}
	
	public LocationNode getFirstCommonNode(GraphPath<LocationNode, DefaultWeightedEdge> one, GraphPath<LocationNode, DefaultWeightedEdge> two){
        List<LocationNode> commonPath = getCommonPath(one, two);
        return commonPath.get(0);
    }
	
	public double getDrivingCost(LocationNode driver, LocationNode commonNode, GraphPath<LocationNode, DefaultWeightedEdge> pathA, GraphPath<LocationNode, DefaultWeightedEdge> pathB){
        double costOne = finder.getPathfinder().getPathWeight(driver, pathA.getStartVertex());
        double costTwo = finder.getPathfinder().getPathWeight(pathA.getStartVertex(), commonNode);
        double costThree = finder.getPathfinder().getPathWeight(commonNode, pathB.getStartVertex());
        return costOne+costTwo+costThree;
    }

    public Driver findNearestTaxiDriver(List<Driver> drivers, GraphPath<LocationNode, DefaultWeightedEdge> path){
        Driver nearestDriver = null;
        double drivingCost = 1000000000;
        for(int i=0; i<drivers.size(); i++){
            double cost = finder.getPathfinder().getPathWeight(drivers.get(i).getLocation(), path.getStartVertex());
            if(cost<drivingCost){
                drivingCost=cost;
                nearestDriver=drivers.get(i);
            }
        }
        return nearestDriver;
    }

    public Driver findBestDriver(GraphPath<LocationNode, DefaultWeightedEdge> pathA, GraphPath<LocationNode, DefaultWeightedEdge> pathB, List<Driver> drivers){
        Driver driverA = findNearestTaxiDriver(drivers, pathA);
        Driver driverB = findNearestTaxiDriver(drivers, pathA);
        LocationNode common = getFirstCommonNode(pathA, pathB);
        double costA = getDrivingCost(driverA.getLocation(), common, pathA, pathB);
        double costB = getDrivingCost(driverB.getLocation(), common, pathB, pathA);
        if(costA>costB){
            return driverB;
        }else {
            return driverA;
        }
    }
}
