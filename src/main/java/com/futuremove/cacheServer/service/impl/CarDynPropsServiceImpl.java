package com.futuremove.cacheServer.service.impl;

import com.futuremove.cacheServer.entity.CarDynProps;
import com.futuremove.cacheServer.entity.CarLocation;
import com.futuremove.cacheServer.entity.Test;
import com.futuremove.cacheServer.service.BaseService;
import com.futuremove.cacheServer.service.CarDynPropsService;
import com.futuremove.cacheServer.service.TestService;
import com.futuremove.cacheServer.utils.ConfigUtils;
import com.futuremove.cacheServer.utils.HttpPostUtils;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.Binary;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by qurj on 15/7/6.
 */
@Component("CarDynPropsService")
public class CarDynPropsServiceImpl extends BaseServiceImpl implements CarDynPropsService {

    final static Logger logger = LoggerFactory.getLogger(CarDynPropsServiceImpl.class);


    @Autowired
    MongoClient mongoClient;



    @Override
    public MongoCollection<Document> getCollection() {
        MongoDatabase database = mongoClient.getDatabase("cacheServer");
        MongoCollection<Document> collection = database.getCollection("carProps");
        return collection;
    }


    public FindIterable<Document> getNearByNeededCar(Long maxDistance,CarDynProps centerFilter) {
        MongoCollection<Document> collection = this.getCollection();
        Document filterDoc = centerFilter.toDocument();
        Document locFilter = new Document();
        locFilter.put("$near",new Document().append("$geometry",centerFilter.location.toDocument())
                                            .append("$maxDistance",maxDistance));
        filterDoc.put("location",locFilter);
        return collection.find(filterDoc);
    }

    public Long countNearByNeededCar(Long maxDistance,CarDynProps centerFilter){
        MongoCollection<Document> collection = this.getCollection();
        Document filterDoc = centerFilter.toDocument();
        Document locFilter = new Document();
        locFilter.put("$near",new Document().append("$geometry",centerFilter.location.toDocument())
                .append("$maxDistance",maxDistance));
        filterDoc.put("location",locFilter);
        return collection.count(filterDoc);
    }


    public FindIterable<Document> getByOwnerAndNotState(CarDynProps carDynProps){
        Document filterDoc = carDynProps.toDocument();
        filterDoc.put("state", new Document().append("$ne",carDynProps.state));
        return getCollection().find(filterDoc);
    }

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test.xml");
        CarDynPropsService service  = (CarDynPropsService)context.getBean("CarDynPropsService");
        CarDynProps props = new CarDynProps();

        props.location = new CarLocation();
        //props.vinNum = "10012";
        props.state = 0;
        props.owner="";
        props.acc=0;
        props.powerPercent = 1.0;
        props.mileage = 1.0;
        props.lockState = 1;
        String [] licenseNum_arr = {
            "京A32145",
                "粤BS1111",
                "粤B66666",
                "粤B88888",
                "粤B86868",
                "京NOPN11",
                "京B0009m",
                "京B00091",
                "京Q7JF17",
                "粤A12345",
                "粤A12445",
                "粤A12645",
                "粤A12745",
                "粤A12845",
                "粤A12045",
                "粤A22345",
                "粤A32345",
                "粤A42345",
                "粤A52345",
                "粤A62345",
                "粤A72345",
                "粤A92345",
                "粤A02345",
                "粤A22045"

        };
        String [] vinNum_arr = {
            "12345116553006992",
                "23456527859110284",
                "12345619005153690",
                "12345619005153691",
                "12345619005153692",
                "12345619005153693",
                "12345619005153694",
                "12345619005153695",
                "10012",
                "JYFMSODA36A201571",
                "JYFMSODA36A201572",
                "JYFMSODA36A201573",
                "JYFMSODA36A201574",
                "JYFMSODA36A201575",
                "JYFMSODA36A201576",
                "JYFMSODA36A201577",
                "JYFMSODA36A201578",
                "JYFMSODA36A201579",
                "JYFMSODA36A201580",
                "JYFMSODA36A201581",
                "JYFMSODA36A201582",
                "JYFMSODA36A201583",
                "JYFMSODA36A201584",
                "JYFMSODA36A201585"
        };
        for(int i=0;i<licenseNum_arr.length;i++) {
            props.licenseNum = licenseNum_arr[i];
            props.vinNum = vinNum_arr[i];
            service.insert(props);
        }

    }

    public boolean sendLock(String vin){
        logger.debug("called lock  command  with vin "+vin);
        try {
            String timeStr = String.valueOf(System.currentTimeMillis());
            String data = "time="+timeStr+"&vin="+vin;
            String url = ConfigUtils.getPropValues("cloudmove.lock");
            String result = HttpPostUtils.post(url, data);

            JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
            int opResult = Integer.parseInt(cmObj.get("result").toString());
            if(opResult==1) {
                logger.debug(" send lock   command  return ok ");
                return true;
            }
            else {
                logger.debug(" send lock  command  return failed ");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;


    }

    public boolean sendPowerOff(String vin) {
        logger.debug("called power off command  with vin "+vin);
        try {


            String timeStr = String.valueOf(System.currentTimeMillis());
            String data = "time="+timeStr+"&vin="+vin;
            String url = ConfigUtils.getPropValues("cloudmove.poweroff");
            String result = HttpPostUtils.post(url, data);

            JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
            int opResult = Integer.parseInt(cmObj.get("result").toString());
            if(opResult==1) {
                logger.debug(" power off command  return ok ");
                return true;
            }
            else {

                logger.debug(" power off command  return failed ");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public boolean sendClearCode(String vin){
        logger.debug("called clear code command with vin " + vin);
        try {
            String timeStr = String.valueOf(System.currentTimeMillis());
            String data = "time="+timeStr+"&vin="+vin;
            String url = ConfigUtils.getPropValues("cloudmove.clearAuth");
            String result = HttpPostUtils.post(url, data);

            JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
            int opResult = Integer.parseInt(cmObj.get("result").toString());
            if(opResult==1) {
                logger.debug(" clear code  command  return ok ");
                return true;
            }
            else {
                logger.debug(" clear code  command  return failed ");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendPowerOn(String vin) {
        logger.debug("called power on  command with vin " + vin);
        try {
            String timeStr = String.valueOf(System.currentTimeMillis());
            String data = "time="+timeStr+"&vin="+vin;
            String url = ConfigUtils.getPropValues("cloudmove.poweron");
            String result = HttpPostUtils.post(url, data);
            JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
            int opResult = Integer.parseInt(cmObj.get("result").toString());
            if(opResult==1) {
                logger.debug(" power on  command  return ok ");
                return true;
            }
            else {
                logger.debug(" power on  command  return failed ");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendAuthCode(String vin) {
        logger.debug("called send auth   command with vin " + vin);
        try {
            String postUrl=ConfigUtils.getPropValues("cloudmove.sendAuth");
            String timeStr = String.valueOf(System.currentTimeMillis());
            String postData = "time=" + timeStr + "&vin=" + vin + "&auth=ABCDEF";
            String result = HttpPostUtils.post(postUrl, postData);
            JSONObject cmObj = (JSONObject)(new JSONParser().parse(result));
            int opResult = Integer.parseInt(cmObj.get("result").toString());
            if(opResult==1) {
                logger.debug(" send auth   command  return ok ");
                return true;
            }
            else {
                logger.debug(" send auth  command  return failed ");
                return false;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
