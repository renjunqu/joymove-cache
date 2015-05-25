package com.futuremove.cacheServer.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;


@Entity("staticmat")
public class StaticMat {
	
	static public Double minLongitude = 116.046096;
	static public Double maxLongtitude = 116.708399;
	static public Double longitudeInterval = 0.000552;
	static public Double minLatitude = 39.692323;
	static public Double maxLatitude = 40.180934;
	static public Double latitudeInterval = 0.000407;
	
	
	@Id
	private ObjectId  _id;
	@Indexed
	private Long   x;
	@Indexed
	private Long   y;
	
	@Embedded
	private List<String> carInfo;

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Long getX() {
		return x;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public List<String> getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(List<String> carInfo) {
		this.carInfo = carInfo;
	}

	public StaticMat(ObjectId _id, Long x, Long y, List<String> carInfo) {
		super();
		this._id = _id;
		this.x = x;
		this.y = y;
		this.carInfo = carInfo;
	}

	public StaticMat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
