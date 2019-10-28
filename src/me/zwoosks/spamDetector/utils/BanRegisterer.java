package me.zwoosks.spamDetector.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;

public class BanRegisterer {
	
	public static void registerBan(String total, String playerName, Main plugin) {
		
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
			
			ps = conn.prepareStatement("INSERT INTO bans(nameOrUUID,reason) VALUES(?,?)");
			
			if(config.getBoolean("online") == true) {
				ps.setString(1, playerName);
				ps.setString(2, total);
				ps.executeUpdate();
				conn.close();
			} else if(config.getBoolean("online") == false) {
				ps.setString(1, playerName.toLowerCase());
				ps.setString(2, total);
				ps.executeUpdate();
				conn.close();
			} else {
				plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to register the player " + playerName + " to the bans table."));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}