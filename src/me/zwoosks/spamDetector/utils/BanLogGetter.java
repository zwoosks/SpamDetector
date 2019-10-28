package me.zwoosks.spamDetector.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;

public class BanLogGetter {
	
	public static String[] getBanLog(String id, Main plugin) {
		
		FileConfiguration config = plugin.getConfig();
		String port = config.getString("port");
		String db = config.getString("database");
		String host = config.getString("host");
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = config.getString("username");
		String password = config.getString("password");
		
		try {
			Connection conn = dbConnection.getConnection(url, username, password);
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM bans_log WHERE nameOrUUID = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String[] res = new String[4];
				res[0] = rs.getString("nameOrUUID");
				res[1] = rs.getString("illegalWord");
				res[2] = rs.getString("illegalText");
				res[3] = rs.getString("whereDidHeWrite");
				conn.close();
				return res;
			}
			conn.close();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
		
	}
	
}