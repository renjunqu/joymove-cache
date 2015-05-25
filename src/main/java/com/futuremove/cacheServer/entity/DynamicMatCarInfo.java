package com.futuremove.cacheServer.entity;

import org.mongodb.morphia.annotations.Embedded;


@Embedded
public class DynamicMatCarInfo {

	private String vinNum;
	private Long eta;
	public String getVinNum() {
		return vinNum;
	}
	public void setVinNum(String vinNum) {
		this.vinNum = vinNum;
	}
	public Long getEta() {
		return eta;
	}
	public void setEta(Long eta) {
		this.eta = eta;
	}
	public DynamicMatCarInfo(String vinNum, Long eta) {
		super();
		this.vinNum = vinNum;
		this.eta = eta;
	}
	public DynamicMatCarInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
