package com.futuremove.cacheServer.test.morphia.entity.event;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity("event")
public abstract class Event {
	 @Id
	 @Indexed( name = "eventId", unique = true)
	 private String eventId;
	 
	 private String type;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Event(String eventId, String type) {
		super();
		this.eventId = eventId;
		this.type = type;
	}

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
}
