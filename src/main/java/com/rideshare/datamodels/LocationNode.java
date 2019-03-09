package com.rideshare.datamodels;

import java.io.Serializable;

public class LocationNode implements Serializable
{

    private double latitude;
    private double longitude;
    private int nodeID;

    public LocationNode(double lat, double longi, int id){
        this.latitude=lat;
        this.longitude=longi;
        this.nodeID=id;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }
}

