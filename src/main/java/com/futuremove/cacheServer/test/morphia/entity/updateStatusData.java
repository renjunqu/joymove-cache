package com.futuremove.cacheServer.test.morphia.entity;

import org.mongodb.morphia.annotations.*;

@Entity("eventData")
public class updateStatusData implements  eventData {
	
	 
	 @Id
	 private String eventId;
	
     private Double latitude;
     private Double longitude; 
     private String type;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public updateStatusData(String eventId, Double latitude, Double longitude,
			String type) {
		super();
		this.eventId = eventId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.type = type;
	}
	public updateStatusData() {
		super();
		// TODO Auto-generated constructor stub
	}
}
