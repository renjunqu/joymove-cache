package com.futuremove.cacheServer.beans;

import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;



public class MQConnFactoryBean  extends AbstractFactoryBean<Connection> {

	
	     private Connection conn =null;
	     private String exchangeName;
	     private String queueName;
	     private String routingKey;
	     private String ip;
	     private String port;
	     
	     @Override
	     protected synchronized Connection createInstance() throws Exception {
	    	 
		    		 if(conn==null) {
		    			
		    			ConnectionFactory factory = new ConnectionFactory();
		    			factory.setHost(this.ip);
		    			factory.setPort(Integer.parseInt(this.port));
		    			factory.setNetworkRecoveryInterval(3000);
		    			factory.setAutomaticRecoveryEnabled(true);
		    			factory.setTopologyRecoveryEnabled(true);
		    			this.conn = factory.newConnection();
		    			Channel channel = conn.createChannel();
		    			channel.exchangeDeclare(exchangeName, "direct", true);
		    			channel.queueDeclare(queueName, false, false, false, null);
		    			channel.queueBind(queueName, exchangeName, routingKey);
		    			
		    	
	    	 }
	    	 return conn;
	        
	     }
		public Connection getConn() {
			return conn;
		}
		public void setConn(Connection conn) {
			this.conn = conn;
		}
		public String getExchangeName() {
			return exchangeName;
		}
		public void setExchangeName(String exchangeName) {
			this.exchangeName = exchangeName;
		}
		public String getQueueName() {
			return queueName;
		}
		public void setQueueName(String queueName) {
			this.queueName = queueName;
		}
		public String getRoutingKey() {
			return routingKey;
		}
		public void setRoutingKey(String routingKey) {
			this.routingKey = routingKey;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getPort() {
			return port;
		}
		public void setPort(String port) {
			this.port = port;
		}
		@Override
		public Class<?> getObjectType() {
			// TODO Auto-generated method stub
			return null;
		}
	 

	
}
