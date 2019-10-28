package me.zwoosks.spamDetector.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbConnection {
	
	public static Connection getConnection(String url, String username, String password) {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(url, username, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
}