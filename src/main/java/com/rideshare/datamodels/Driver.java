package com.rideshare.datamodels;

public class Driver 
{
	private LocationNode location;
	private int id;

	public Driver(LocationNode locationNode, int id){
        this.location=locationNode;
        this.id=id;
    }

    public LocationNode getLocation() {
        return location;
    }

    public void setLocation(LocationNode location) {
        this.location = location;
    }
    
    public int getId() {
  		return id;
  	}

  	public void setId(int id) {
  		this.id = id;
  	}
}
