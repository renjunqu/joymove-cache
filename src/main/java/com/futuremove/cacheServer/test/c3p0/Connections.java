package com.futuremove.cacheServer.test.c3p0;
import java.beans.PropertyVetoException;  
import java.sql.Connection;  
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;  
import java.sql.Statement;
  
/** 
 * 数据库连接池 
 *  
 * @author tntxia 
 *  
 */  
public class Connections {  
  
/*  
    private static ComboPooledDataSource ds = new ComboPooledDataSource();  
 
    static {  
        ds.setJdbcUrl("jdbc:mysql://123.56.106.52:3306/joymove_qrj");  
        ds.setUser("root");  
        ds.setPassword("123456");  
        ds.setAcquireIncrement(15);  
        ds.setInitialPoolSize(20);  
        ds.setMinPoolSize(10);  
        ds.setMaxPoolSize(500);  
        ds.setAcquireRetryAttempts(0);  
        ds.setMaxIdleTime(10);  
        ds.setCheckoutTimeout(5000);  
  
        try {  
            ds.setDriverClass("com.mysql.jdbc.Driver");  
        } catch (PropertyVetoException e) {  
  
        }  
        ds.setMaxStatements(0);  
    }  
   
    
    public static void main(String [] args){
    	
    	Connection conn = null;
    	
    	try {
			conn = ds.getConnection();
			Statement stat = conn.createStatement();
			String query = "SELECT * FROM JOY_Car";
			ResultSet rs = stat.executeQuery(query);
			while(rs.next()){
				
				logger.trace(rs.getString("id"));
			}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn = ds.getConnection();
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
    	
    	logger.trace("hello world");
    	
    }
    */
}  
