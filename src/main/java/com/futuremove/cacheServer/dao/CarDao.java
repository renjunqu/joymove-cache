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

import com.futuremove.cacheServer.entity.Car;
import com.futuremove.cacheServer.entity.DynamicMat;
import com.futuremove.cacheServer.service.CarService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Repository
public class CarDao  {
	
	final static Logger logger = LoggerFactory.getLogger(CarDao.class);

	@Resource(name = "datastore")
    private Datastore datastore;
	
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
		List<Car> carList = q.asList();
		if(carList.size()==0)
			return null;
		else
			return q.asList().get(0);
	}

	public void save(Car car) {
		// TODO Auto-generated method stub
		datastore.save(car);
	}
	//change car state to new_state, if now state equals old_state,if old_state not null
	//and set owner of car.getOwner not null
	public void changeCarState(Integer new_state,Car car) {
		// TODO Auto-generated method stub
		logger.debug("dao : update State to "+new_state+" start");
		UpdateOperations<Car> ops = datastore.createUpdateOperations(Car.class)
				.set("state",new_state);
		if(car.getOwner() !=null) {
			ops.set("owner",car.getOwner());
		}

		Query<Car> q = datastore.createQuery(Car.class)
				.field("vinNum").equal(car.getVinNum());
		if(car.getState()!=null) {
			q.field("state").equals(car.getState());
		}
		datastore.update(q, ops);
		logger.debug("dao : update State to" + new_state + " over ");


	}

	//set car state to free, when car's state as specified


	public void updateCarStateFree(Car car) {
		this.changeCarState(Car.state_free,car);
	}
	//set car state to wait_code, when car's state as free or reserved, that means specified
	public void updateCarStateWaitCode(Car car) {
		car.setState(Car.state_free);
		this.changeCarState(Car.state_wait_code, car);
	}

	//set car state to pending, when car's state as free
	public void updateCarStateReservePending(Car car) {
		car.setState(Car.state_free);
		this.changeCarState(Car.state_reserve_pending,car);

	}
		
	//set car state to reserved, when car's state as pending
	public void updateCarStateReserved(Car car) {
		car.setState(Car.state_reserve_pending);
		this.changeCarState(Car.state_reserved,car);

	}
	
	//set car state to reserved, when car's state as pending
	public void updateCarStateBusy(Car car) {
		this.changeCarState(Car.state_busy,car);

	}

	public void  updateCarStatePowerOn(Car car){
		car.setState(car.state_free);
		this.changeCarState(Car.state_wait_power,car);
	}

	public void  updateCarStateWaitLock(Car car){
		this.changeCarState(Car.state_wait_lock,car);
	}







	public static void main(String [] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cacheServerBeans.xml");

		
		
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
