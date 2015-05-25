package com.futuremove.cacheServer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;


public class HttpPostUtils {

	
	public static String post(String url,JSONObject json){
	    return post(url,json.toJSONString(),"application/json");
	}
	
	public static String post(String url,String data){
		return post(url,data,"application/x-www-form-urlencoded;charset=utf-8");
	}

	public static String post(String url,String data,String contentType){
	
	
		try {
			HttpPost post = new HttpPost(url);
			
			StringEntity  postingString =new StringEntity(data);
			post.setEntity(postingString);
			post.setHeader("Content-type", contentType);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpResponse response;
			response = httpclient.execute(post);
		
			HttpEntity httpEntity=response.getEntity();
			StringBuffer entityStringBuilder = new StringBuffer();
	        if (httpEntity!=null) {  
	        	BufferedReader  bufferedReader=new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8*1024);  
	        	StringBuffer jb = new StringBuffer();
	   		 	String line = null;
	   			 while ((line = bufferedReader.readLine()) != null)
	   				  jb.append(line);
	   			 String jstr = jb.toString();
	   			 
	   			 return jstr;
	        }  
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//access my own interface, it is ok	
		return null;
	}

}
