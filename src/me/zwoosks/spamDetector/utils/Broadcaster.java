package me.zwoosks.spamDetector.utils;

import org.bukkit.entity.Player;

import me.zwoosks.spamDetector.Main;

public class Broadcaster {
	
	public static void bannedAlert(String s1, String s2, Main plugin, String permission) {
		for(Player player : plugin.getServer().getOnlinePlayers()) {
			if(player.hasPermission(permission)) {
				player.sendMessage(s1);
				player.sendMessage(s2);
			}
		}
	}
	
}