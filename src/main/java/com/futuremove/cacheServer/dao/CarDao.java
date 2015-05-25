package com.futuremove.cacheServer.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.futuremove.cacheServer.amqp.handler.impl.RegisterHandler;
import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.entity.DynamicMat;
import com.futuremove.cacheServer.service.CarService;

public class CarDao  {
	
	final static Logger logger = LoggerFactory.getLogger(CarDao.class);
	
	
    private Datastore datastore;
    
	
	
	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
	
	

	public CarDao(Datastore datastore) {
		super();
		this.datastore = datastore;
	}

	
	
	public CarDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	/******* business proc *************/

	public void updateLocInfo(Car car) {
		logger.debug("update Loc Info start ");
		// TODO Auto-generated method stub
		UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
				.set("longitude",car.getLongitude()).set("latitude",car.getLatitude());
		
		Query<Car> q = datastore.createQuery(Car.class).field("vinNum")
				.equal(car.getVinNum());
		//do not use create if missing,this item should be create when regitered
		
		datastore.update(q, ops);
		logger.debug("update Loc Info over ");
	}

	public Car get(String vinNum) {
		// TODO Auto-generated method stub
		Query<Car> q = datastore.createQuery(Car.class).field("vinNum")
				.equal(vinNum);
		return q.asList().get(0);
	}

	public void save(Car car) {
		// TODO Auto-generated method stub
		datastore.save(car);
	}
	//set car state to free, when car's state as specified
	public void updateCarStateFree(Car car) {
		// TODO Auto-generated method stub
		logger.debug("dao : update State to Free start");
		 UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
				.set("state",Car.state_free);
		Query<Car> q = datastore.createQuery(Car.class)
				.field("vinNum").equal(car.getVinNum())
				.field("state").equal(car.getState());
		datastore.update(q, ops);
		logger.debug("dao : update State to Free over ");	
		
	}
	//set car state to wait_code, when car's state as free or reserved, that means specified
	public void updateCarStateWaitCode(Car car) {
		// TODO Auto-generated method stub
		logger.debug("dao : update State to Pending start");
		 UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
				.set("state",Car.state_wait_code)
				.set("owner", car.getOwner());
		Query<Car> q = datastore.createQuery(Car.class)
				.field("vinNum").equal(car.getVinNum())
				.field("state").equal(car.getState());
		datastore.update(q, ops);
		logger.debug("dao : update State to Pending over ");	
	} 
	
	
	
	//set car state to pending, when car's state as free
	public void updateCarStateReservePending(Car car) {
		// TODO Auto-generated method stub
		logger.debug("dao : update State to Pending start");
		 UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
				.set("state",Car.state_reserve_pending)
				.set("owner", car.getOwner());
		Query<Car> q = datastore.createQuery(Car.class)
				.field("vinNum").equal(car.getVinNum())
				.field("state").equal(Car.state_free);
		datastore.update(q, ops);
		logger.debug("dao : update State to Pending over ");	
	}
		
	//set car state to reserved, when car's state as pending
	public void updateCarStateReserved(Car car) {
			// TODO Auto-generated method stub
			logger.debug("dao : update State to reserved start");
			UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
						.set("state",Car.state_reserved);
			Query<Car> q = datastore.createQuery(Car.class)
						.field("vinNum").equal(car.getVinNum())
						.field("state").equal(Car.state_reserve_pending);
			datastore.update(q, ops);
			logger.debug("dao : update State to reserved over ");	
	}
	
	//set car state to reserved, when car's state as pending
	public void updateCarStateBusy(Car car) {
				// TODO Auto-generated method stub
			logger.debug("dao : update State to Busy start");
			UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
					.set("state",Car.state_busy);
			Query<Car> q = datastore.createQuery(Car.class)
					.field("vinNum").equal(car.getVinNum())
					.field("state").equal(Car.state_wait_code);
			datastore.update(q, ops);
			logger.debug("dao : update State to Busy over ");	
	}
	
	

	public void updateCarState(Car car) {
		// TODO Auto-generated method stub
		logger.debug("dao : update State Info start");
		UpdateOperations<Car> ops = null;
		if(car.getOwner()!=null) {
		ops = datastore.createUpdateOperations(Car.class)
				.set("state",car.getState()).set("owner", car.getOwner());
		} else {
		ops = datastore.createUpdateOperations(Car.class)
					.set("state",car.getState());
			
		}
		
		Query<Car> q = datastore.createQuery(Car.class).field("vinNum")
				.equal(car.getVinNum());
		datastore.update(q, ops);
		logger.debug("dao : update State Info over ");	
	}
	
	public static void main(String [] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");
		CarDao  carDao  = (CarDao)context.getBean("carDao");
		StaticMatDao  staticMatDao  = (StaticMatDao)context.getBean("staticMatDao");
		DynamicMatDao  dynamicMatDao  = (DynamicMatDao)context.getBean("dynamicMatDao");
		CarService carService = (CarService)context.getBean("carService");
		Datastore datastore = (Datastore)context.getBean("datastore");
		
		Car car  = new Car();
		car.setVinNum("dsfdsf");
		car.setState(0);
		car.setOwner("");
		car.setLatitude(49.9);
		
		car.setLongitude(116.049);
		carService.save(car);
		carService.updateCarPosition(car);
		car.setState(Car.state_busy);
		carService.updateCarState(car);
		carService.updateCarPosition(car);
		car.setState(Car.state_free);
		carService.updateCarState(car);
		carService.updateCarPosition(car);
		System.out.println("over");
		
		
	}

	public void clearExpireReserve(Car car) {
		// TODO Auto-generated method stub
		logger.debug("dao : update clearExpireReserve  start");
		// TODO Auto-generated method stub
		UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
				.set("state",Car.state_free);
				
		Query<Car> q = datastore.createQuery(Car.class)
				.field("vinNum").equal(car.getVinNum())
				.field("owner").equal(car.getOwner())
				.field("state").equal(Car.state_reserved);
		//do not use create if missing,this item should be create when regitered
		datastore.update(q, ops);
		logger.debug("update Loc Info over ");
		
	}

	public Car getByOwnerAndNotState(Car car) {
		// TODO Auto-generated method stub
		Query<Car> q = datastore.createQuery(Car.class).field("owner")
				.equal(car.getOwner()).field("state").notEqual(car.getState());
		List<Car> cars = q.asList();
		if(cars.size()>0) {
			return cars.get(0);
		} else {
			return null;
		}
		
	}


}
