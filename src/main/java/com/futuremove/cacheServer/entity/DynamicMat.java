package com.futuremove.cacheServer.entity;

import java.util.List;



import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;


/*
The northest : 116.35885,40.180934
The southest : 116.322056,39.692323
  distance from north to sourth :  54.3919 km
                         543.919 --> 1200 mat elements 50m one element distance
     40.180934 - 39.692323 = 0.488611  ---> 488611 --> 407 ---> 0.000407  
   the longitude interval
 
The westest : 116.046096,39.997232
The eastest : 116.708399,39.961848
   distance from west to east:  56.7319 km
                         567.319 --> 1200 mat elements   50m one element distance 
     116.708399 - 116.046096 = 0.662303 ---> 662303 --> 552 --> 0.000552
    the latitude interval
*/


@Entity("dynamicmat")
public class DynamicMat {
	
	
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
	private List<DynamicMatCarInfo> carInfo;

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

	public List<DynamicMatCarInfo> getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(List<DynamicMatCarInfo> carInfo) {
		this.carInfo = carInfo;
	}

	public DynamicMat(ObjectId _id, Long x, Long y, List<DynamicMatCarInfo> carInfo) {
		super();
		this._id = _id;
		this.x = x;
		this.y = y;
		this.carInfo = carInfo;
	}

	public DynamicMat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	
}
