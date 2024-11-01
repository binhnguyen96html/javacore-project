package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;



public class ConnectionUtils {
	
	private static String DB_URL = "jdbc:mysql://localhost/estate_ex3_javacore";
	private static String USER = "root";
	private static String PASS = "Ilovejob123@";
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
		} catch (SQLException e) {
			return null;
		}catch(Exception e) {
			return null;
		}
	}
	
	 public static boolean isNotEmpty(Collection<?> collection) {
	        return collection != null && !collection.isEmpty();
	    }
}
