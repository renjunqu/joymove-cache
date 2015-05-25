package com.futuremove.cacheServer.test.morphia.entity.event;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;


@Entity("event")
public class RegisterEvent  extends  Event {
	
	 private Date registerTime;
	 private String vinNum;
	
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getVinNum() {
		return vinNum;
	}
	public void setVinNum(String vinNum) {
		this.vinNum = vinNum;
	}
	public RegisterEvent(String eventId, String type,Date registerTime, String vinNum) {
		super(eventId,type);
		this.registerTime = registerTime;
		this.vinNum = vinNum;
	}
	public RegisterEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
}
