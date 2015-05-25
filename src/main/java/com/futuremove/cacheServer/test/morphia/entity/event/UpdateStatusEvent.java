package com.futuremove.cacheServer.test.morphia.entity.event;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;



@Entity("event")
public class UpdateStatusEvent  extends  Event {
	
	
     private Double latitude;
     private Double longitude; 
   
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public UpdateStatusEvent(String eventId, Double latitude, Double longitude,
			String type) {
		super(eventId,type);
		
		this.latitude = latitude;
		this.longitude = longitude;
		
	}
	public UpdateStatusEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
