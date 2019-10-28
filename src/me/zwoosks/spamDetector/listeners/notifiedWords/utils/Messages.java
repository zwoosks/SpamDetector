package me.zwoosks.spamDetector.listeners.notifiedWords.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.zwoosks.spamDetector.utils.Utils;

public class Messages {
	
	public static void sendToPlayer(String whereDidHeWrite, String illegalWord, String text, Player player, FileConfiguration config) {
		if(config.getBoolean(whereDidHeWrite + ".alert.messages.player.notifyPlayer")) {
			String toSend = config.getString(whereDidHeWrite + ".alert.messages.player.message").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", player.getName());
			player.sendMessage(Utils.chat(toSend));
		}
	}
	
	public static void alertStaff(String whereDidHeWrite, String illegalWord, String text, Player player, FileConfiguration config) {
		if(config.getBoolean(whereDidHeWrite + ".alert.messages.staff.notifyStaff")) {
			String toSend = config.getString(whereDidHeWrite + ".alert.messages.staff.message").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", player.getName());
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(p.hasPermission("antispam.notify.alert." + whereDidHeWrite)) {
					p.sendMessage(Utils.chat(toSend));
				}
			}
		}
	}
}