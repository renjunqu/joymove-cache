package com.futuremove.cacheServer.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.entity.DynamicMat;
import com.futuremove.cacheServer.entity.DynamicMatCarInfo;
import com.futuremove.cacheServer.entity.StaticMat;


public class StaticMatDao   {

	
	final static Logger logger = LoggerFactory.getLogger(StaticMatDao.class);
	
    private Datastore datastore;
    
	
	
	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
	
	

	public StaticMatDao(Datastore datastore) {
		super();
		this.datastore = datastore;
	}

	
	
	public StaticMatDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	/******* business proc *************/
	public static Long getXIndex(Double x){
		Double diff = StaticMat.maxLongtitude - x ;
		Double index = diff / StaticMat.longitudeInterval;
		return index.longValue();
	}
	
    public static Long getYIndex(Double y){
    	Double diff = StaticMat.maxLatitude - y ;
		Double index = diff / StaticMat.latitudeInterval;
		return index.longValue();	
	}
    

	public void updateCarInfo(Car prevCar, Car car) {
		// TODO Auto-generated method stub
		         logger.debug("update car info");
			
		     	//first delete the old one
				if(prevCar != null ) {
					
					Long oldX = getXIndex(prevCar.getLongitude());
					Long oldY = getYIndex(prevCar.getLatitude());
					logger.debug("old car remove with x "+oldX+" y "+oldY);
					Query<StaticMat> q = datastore.createQuery(StaticMat.class)
							.field("x").equal(oldX).field("y").equal(oldY);
					UpdateOperations<StaticMat> ops = datastore.createUpdateOperations(StaticMat.class)
							.removeAll("carInfo", prevCar.getVinNum());
					datastore.findAndModify(q, ops);
				}
				//then add the new one
				if(car !=null) {
					Long newX = getXIndex(car.getLongitude());
					Long newY = getYIndex(car.getLatitude());
					logger.debug("new car added with x "+ newX +" y "+ newY);
					Query<StaticMat> q = datastore.createQuery(StaticMat.class)
							.field("x").equal(newX).field("y").equal(newY);
					UpdateOperations<StaticMat> ops = datastore.createUpdateOperations(StaticMat.class)
							.add("carInfo", car.getVinNum());
					//create if missing
					datastore.update(q, ops,true);
				}
		
	}

	public void removeCar(Car car) {
		// TODO Auto-generated method stub
		Query<StaticMat> q = datastore.createQuery(StaticMat.class).field("carInfo").hasThisOne(car.getVinNum());
		UpdateOperations<StaticMat> ops = datastore.createUpdateOperations(StaticMat.class)
				.removeAll("carInfo", car.getVinNum());
		datastore.findAndModify(q, ops);
	
	}

	public List<String> getCarVinNumByScope(Long minX, Long maxX, Long minY,
			Long maxY) {
		// TODO Auto-generated method stub
		Query<StaticMat> q = (Query<StaticMat>) datastore.createQuery(StaticMat.class)
				.field("x").greaterThan(minX)
				.field("x").lessThan(maxX)
				.field("y").greaterThan(minY)
				.field("y").lessThan(maxY);
		List<StaticMat> mats  = q.asList();
		List<String> carVinNums =  new ArrayList<String>();
		//first iterator
		
		if(mats!=null) {
			for(StaticMat mat:mats) {
				if(mat.getCarInfo()!=null)
					carVinNums.addAll(mat.getCarInfo());
			}
		}
		
		

		return carVinNums;
	}


}
