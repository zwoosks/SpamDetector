package me.zwoosks.spamDetector.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;

public class BansLoggeer {
	
	public static void logBan(String id, String illegalWord, String illegalText, String whereDidHeWrite, Main plugin) {
		
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
			
			ps = conn.prepareStatement("INSERT INTO bans_log(nameOrUUID,illegalWord,illegalText,whereDidHeWrite) VALUES(?,?,?,?)");
			
			if(config.getBoolean("online") == true) {
				ps.setString(1, id.replace("-", ""));
				ps.setString(2, illegalWord);
				ps.setString(3, illegalText);
				ps.setString(4, whereDidHeWrite);
				ps.executeUpdate();
				conn.close();
			} else if(config.getBoolean("online") == false) {
				ps.setString(1, id.toLowerCase());
				ps.setString(2, illegalWord);
				ps.setString(3, illegalText);
				ps.setString(4, whereDidHeWrite);
				ps.executeUpdate();
				conn.close();
			} else {
				plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to register the player ban info (" + id + ") to the bans table."));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}