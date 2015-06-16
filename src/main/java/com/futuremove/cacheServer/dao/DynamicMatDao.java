package com.futuremove.cacheServer.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.bson.types.ObjectId;

import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.entity.DynamicMat;
import com.futuremove.cacheServer.entity.DynamicMatCarInfo;
import com.futuremove.cacheServer.entity.StaticMat;
import com.futuremove.cacheServer.service.CarService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class DynamicMatDao{
	
	final static Logger logger = LoggerFactory.getLogger(DynamicMatDao.class);

	@Resource(name = "datastore")
     private Datastore datastore;


	
	
	public DynamicMatDao() {
		super();
		// TODO Auto-generated constructor stub
	}

	/******* business proc *************/
	public static Long getXIndex(Double x){
		Double diff = DynamicMat.maxLongtitude - x ;
		Double index = diff / DynamicMat.longitudeInterval;
		return index.longValue();
	}
	
    public static Long getYIndex(Double y){
    	Double diff = DynamicMat.maxLatitude - y ;
		Double index = diff / DynamicMat.latitudeInterval;
		return index.longValue();	
	}

	
	
	//update and add
	public void updateCurrCarInfo(Car prevCar, Car car) {
		// TODO Auto-generated method stub
		
		logger.debug("update car info");
	
     	//first delete the old one
		if(prevCar != null ) {
			Long oldX = getXIndex(prevCar.getLongitude());
			Long oldY = getYIndex(prevCar.getLatitude());
			logger.debug("old car remove with x "+oldX+" y "+oldY);
			Query<DynamicMat> q = datastore.createQuery(DynamicMat.class).field("carInfo.vinNum")
					.equal(prevCar.getVinNum()).field("x").equal(oldX).field("y").equal(oldY)
					.retrievedFields(true, "carInfo.$");
			List<DynamicMat> mats = q.asList();
			if(mats.size()>0) {
				DynamicMat mat = mats.get(0);
				DynamicMatCarInfo info = mat.getCarInfo().get(0);
				UpdateOperations<DynamicMat> ops = datastore.createUpdateOperations(DynamicMat.class)
						.removeAll("carInfo", info);
				datastore.findAndModify(q, ops);
			}
		}
		//then add the new one
		if(car !=null) {
			Long newX = getXIndex(car.getLongitude());
			Long newY = getYIndex(car.getLatitude());
			logger.debug("new car added with x "+ newX +" y "+ newY);
			DynamicMatCarInfo info = new DynamicMatCarInfo(car.getVinNum(),new Long(0));
			Query<DynamicMat> q = datastore.createQuery(DynamicMat.class)
					.field("x").equal(newX).field("y").equal(newY);
			UpdateOperations<DynamicMat> ops = datastore.createUpdateOperations(DynamicMat.class)
					.add("carInfo", info);
			//create if missing
			datastore.update(q, ops,true);
		}
	}



	public void removeCar(Car car) {
		// TODO Auto-generated method stub
		
		Query<DynamicMat> q = datastore.createQuery(DynamicMat.class).field("carInfo.vinNum")
				.equal(car.getVinNum()).retrievedFields(true, "carInfo.$");
		List<DynamicMat> elements = q.asList();
		List<DynamicMatCarInfo> infoList = new ArrayList<DynamicMatCarInfo>();
		if(elements.size()>0) {
			for(DynamicMat ele:elements){
				infoList.addAll(ele.getCarInfo());
			}
			UpdateOperations<DynamicMat> ops = datastore.createUpdateOperations(DynamicMat.class)
					.removeAll("carInfo", infoList);
			datastore.findAndModify(q, ops);
		}
	}
	
	public static void main(String [] args){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
		DynamicMatDao dynamicMatDao = (DynamicMatDao)context.getBean("dynamicMatDao");
		 Map<String,Object> likeCondition = new HashMap<String, Object>();
		 Car car = new Car();
		 car.setVinNum("22222222222222222");
		 dynamicMatDao.getCarVinNumByScope(new Long(1),new Long(2),new Long(3),new Long(4));
		 
	}

	public List<DynamicMatCarInfo> getCarVinNumByScope(Long minX, Long maxX, Long minY,
			Long maxY) {
		// TODO Auto-generated method stub
		Query<DynamicMat> q = (Query<DynamicMat>) datastore.createQuery(DynamicMat.class)
				.field("x").greaterThan(minX)
				.field("x").lessThan(maxX)
				.field("y").greaterThan(minY)
				.field("y").lessThan(maxY);
		List<DynamicMat> mats = q.asList();
		//logger.trace(mats);
		//logger.trace(mats.size());
		List<DynamicMatCarInfo> infos = new ArrayList<DynamicMatCarInfo>();
		if(mats!=null) {
			for(DynamicMat mat:mats) {
				    if(mat.getCarInfo()!=null)
				    	infos.addAll(mat.getCarInfo());
			}
		}
		return infos;
	}

}
