package com.futuremove.cacheServer.test.morphia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.entity.DynamicMatCarInfo;
import com.futuremove.cacheServer.entity.DynamicMat;
import com.futuremove.cacheServer.test.morphia.entity.event.RegisterEvent;

public class testMorphia2 {
	
	public static void main(String [] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
		Datastore  datastore  = (Datastore)context.getBean("datastore");
		/*
		JOYDynMat mat = new JOYDynMat();
		mat.setX(new Long(1));
		mat.setY(new Long(2));
		JOYCarDynInfo info = new JOYCarDynInfo();
		info.setVinNum("dsfdsf");
		info.setEta(new Long(12345));	
		List<JOYCarDynInfo> infos =new ArrayList<JOYCarDynInfo> ();
		infos.add(info);
		mat.setCarInfo(infos);
		datastore.save(mat);
		//datastore.update
		*/
		
		DynamicMatCarInfo info = new DynamicMatCarInfo();
		info.setVinNum("qqqq1");
		info.setEta(new Long(12345));
		
		/*
		UpdateOperations<DynamicMat> ops = datastore.createUpdateOperations(DynamicMat.class)
				.add("carInfo", info);
		Query<DynamicMat> q = datastore.createQuery(DynamicMat.class).field("x")
				.equal(new Long(1)).field("y").equal(new Long(2));
		*/
		
			
		
		Query<DynamicMat> q2 = datastore.createQuery(DynamicMat.class).field("carInfo.vinNum")
				.equal("qqqq").field("x").equal(new Long(1)).field("y").equal(new Long(2))
				.retrievedFields(true, "carInfo.$");
		DynamicMat mat = q2.asList().get(0);
		info = mat.getCarInfo().get(0);
		UpdateOperations<DynamicMat> ops2 = datastore.createUpdateOperations(DynamicMat.class)
				.removeAll("carInfo", info);
		datastore.findAndModify(q2, ops2);
		
		
		//datastore.findAndDelete(q2);
		
		
		//datastore.update(q, ops,true);
		
	//	logger.trace("over");
	}

}
