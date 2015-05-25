package com.futuremove.cacheServer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;

public class HttpGetUtils {
	public static String get(String url){
	
		
		try {
			HttpGet get = new HttpGet(url);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpResponse response;
			response = httpclient.execute(get);
		
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
