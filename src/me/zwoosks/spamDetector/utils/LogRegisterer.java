package me.zwoosks.spamDetector.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;

public class LogRegisterer {
	
	@SuppressWarnings("deprecation")
	public static void registerLog(String playerName, String illegalWord, String text, String where, Main plugin) {
		
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
			
			ps = conn.prepareStatement("INSERT INTO logs(playerName,illegalWord,text,whereDidHeWrite) VALUES(?,?,?,?)");
			
			if(config.getBoolean("online") == true) {
				ps.setString(1, Bukkit.getServer().getPlayer(playerName).getUniqueId().toString().replace("-", ""));
				ps.setString(2, illegalWord);
				ps.setString(3, text);
				ps.setString(4, where);
				ps.executeUpdate();
				conn.close();
			} else if(config.getBoolean("online") == false) {
				ps.setString(1, playerName.toLowerCase());
				ps.setString(2, illegalWord);
				ps.setString(3, text);
				ps.setString(4, where);
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