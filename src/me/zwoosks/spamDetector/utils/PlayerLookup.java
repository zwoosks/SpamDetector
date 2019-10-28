package me.zwoosks.spamDetector.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;

public class PlayerLookup {
	
	public static boolean isBanned(String index, Main plugin) {
		
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
			
			ps = conn.prepareStatement("SELECT * FROM bans WHERE nameOrUUID = ?");
			ps.setString(1, index);
			
			ResultSet rs;
			rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}