package me.zwoosks.spamDetector.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;

public class BanClearer {
	
	public static void clearBanLog(String index, Main plugin) {
		
		FileConfiguration config = plugin.getConfig();
		
		// FER MYSQL
		String port = config.getString("port");
		String db = config.getString("database");
		String host = config.getString("host");
		
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = config.getString("username");
		String password = config.getString("password");
		
		try {
			Connection conn = dbConnection.getConnection(url, username, password);
			PreparedStatement ps;
			
			ps = conn.prepareStatement("DELETE FROM bans_log WHERE (nameOrUUID = ?)");
			
			ps.setString(1, index);
			ps.executeUpdate();
			conn.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void clearBan(String index, Main plugin) {
		
		FileConfiguration config = plugin.getConfig();
		
		// FER MYSQL
		String port = config.getString("port");
		String db = config.getString("database");
		String host = config.getString("host");
		
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = config.getString("username");
		String password = config.getString("password");
		
		try {
			Connection conn = dbConnection.getConnection(url, username, password);
			PreparedStatement ps;
			
			ps = conn.prepareStatement("DELETE FROM bans WHERE (nameOrUUID = ?)");
			
			ps.setString(1, index);
			ps.executeUpdate();
			conn.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}