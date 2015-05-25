package com.futuremove.cacheServer.amqp.listeners;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.Method;

public class CMShutdownListener implements ShutdownListener  {

	@Override
	public void shutdownCompleted(ShutdownSignalException cause) {
		// TODO Auto-generated method stub
		if (cause.isHardError())
		  {
			System.out.println("amq: -- hareware error");
		    Connection conn = (Connection)cause.getReference();
		    if (!cause.isInitiatedByApplication())
		    {
		      Method reason = (Method)cause.getReason();
		      System.out.println("amq: reason : "+cause.getReason().toString());
		      
		    } else {
		    	System.out.println("shutdown by app itself");
		    }
		    
		  } else {
			System.out.println("amq: -- not hareware error");
		    Channel ch = (Channel)cause.getReference();
		    System.out.println("amq: reason : "+cause.getReason().toString());
		    Method reason = (Method)cause.getReason();
		    
		  }
		
		
	}
	

}
