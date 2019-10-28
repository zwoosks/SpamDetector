package me.zwoosks.spamDetector.listeners.bannedWords;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.mySQL.dbConnection;
import me.zwoosks.spamDetector.utils.Utils;

public class JoinListener implements Listener {
	
private Main plugin;
	
	public JoinListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		FileConfiguration config = plugin.getConfig();
		String port = config.getString("port");
		String db = config.getString("database");
		String host = config.getString("host");
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = config.getString("username");
		String password = config.getString("password");
		try {
			Connection conn = dbConnection.getConnection(url, username, password);
			boolean online = config.getBoolean("online");
			if(online) {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM bans WHERE nameOrUUID = ?");
				ps.setString(1, player.getUniqueId().toString().replace("-", ""));
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					String display = rs.getString("reason");
					player.kickPlayer(display);
				}
			} else if(!online) {
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM bans WHERE nameOrUUID = ?");
				ps.setString(1, player.getName().toLowerCase());
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					String display = rs.getString("reason");
					player.kickPlayer(display);
				}
			} else {
				plugin.getLogger().info(Utils.chat("&c&l(!) 'online' variable doesn't exist in config.yml!"));
			}
			conn.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}