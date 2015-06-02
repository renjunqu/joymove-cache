package com.futuremove.cacheServer.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Version;

@Entity("car")
public class Car {
	
	public static int state_free = 0;
	public static int state_reserved  = 1;
	public static int state_busy = 2;
	public static int state_wait_code = 3;
	//its used to two-phase commit
	public static int state_reserve_pending = 4;
	//等待开火
	public static int state_wait_power = 5;
	//等待关火
	public  static int state_wait_off = 6;
    //等待锁车成功
	public static int state_wait_lock = 7;
	
	@Id
	@Indexed
	private String vinNum;
	
	private Double longitude;
	private Double latitude;
	private Double desLongitude;
	private Double desLatitude;
	
	
	/* used for prevent conconrrent access*/
	@Version
	private Long   version;
	
	
	@Indexed
	private String owner;//reserver or renter
	
	
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	private Integer state;
	public String getVinNum() {
		return vinNum;
	}
	public void setVinNum(String vinNum) {
		this.vinNum = vinNum;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getDesLongitude() {
		return desLongitude;
	}
	public void setDesLongitude(Double desLongitude) {
		this.desLongitude = desLongitude;
	}
	public Double getDesLatitude() {
		return desLatitude;
	}
	public void setDesLatitude(Double desLatitude) {
		this.desLatitude = desLatitude;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Car(String vinNum, Double longitude, Double latitude,
			Double desLongitude, Double desLatitude, Long version, Integer state,String owner) {
		super();
		this.vinNum = vinNum;
		this.longitude = longitude;
		this.latitude = latitude;
		this.desLongitude = desLongitude;
		this.desLatitude = desLatitude;
		this.version = version;
		this.state = state;
		this.owner = owner;
	}
	public Car() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
