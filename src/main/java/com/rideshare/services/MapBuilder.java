package com.rideshare.services;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rideshare.datamodels.LocationNode;

@Service
public class MapBuilder
{
	public Graph<LocationNode, DefaultWeightedEdge> creatMap() throws IOException {
        Graph<LocationNode, DefaultWeightedEdge> map = new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
        String fileName = "/home/randika/Documents/mapCordinates";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        System.out.println("Read text file using Scanner");
//read line by line
        int i = 0;
        while(scanner.hasNextLine()){
            //process each line
            i=i+1;
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            Double longitude = Double.parseDouble(words[2]);
            Double lattitude = Double.parseDouble(words[3]);
//            System.out.println(longitude);
//            System.out.println(words[1]);
            LocationNode locationNode = new LocationNode(lattitude, longitude, i);
            map.addVertex(locationNode);
//            System.out.println(findNodeById(map.vertexSet(), i));


        }
        scanner.close();
        return map;
    }

    public LocationNode findNodeById(Set<LocationNode> nodes, int id){
        for(LocationNode node: nodes){
            if(node.getNodeID()==id){
                return node;
            }
        }
        return null;
    }

    public Graph addEdge() throws IOException {
        Graph<LocationNode, DefaultWeightedEdge> map = creatMap();
        String fileName = "/home/randika/Documents/mapWeight";
        Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(path);
        System.out.println("Read text file using Scanner");
        int i = 0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            int vone = Integer.parseInt(words[1]);
            int vtwo = Integer.parseInt(words[2]);
            Double time = Double.parseDouble(words[3]);
            DefaultWeightedEdge edge = map.addEdge(findNodeById(map.vertexSet(), vone), findNodeById(map.vertexSet(), vtwo));
            map.setEdgeWeight(edge, time);
            System.out.println(vone);
//            System.out.println(vtwo);
//            System.out.println(time);

        }
        scanner.close();
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("mapObject.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void saveGraphObject(Graph<LocationNode, DefaultWeightedEdge> map){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("/home/randika/Documents/map.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(map);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Graph<LocationNode, DefaultWeightedEdge> readMapObject(){
        try {
            FileInputStream fileIn = new FileInputStream("mapObject.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Graph<LocationNode, DefaultWeightedEdge> map =  (Graph<LocationNode, DefaultWeightedEdge>) in.readObject();
            in.close();
            fileIn.close();
            return map;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }


}
