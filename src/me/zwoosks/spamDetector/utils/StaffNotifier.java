package me.zwoosks.spamDetector.utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;

public class StaffNotifier {
	
	public static void notifyBanned(String playerName, String whereDidHeWrite, String text, String illegalWord, Main plugin) {
		
		FileConfiguration config = plugin.getConfig();
		
		switch (whereDidHeWrite) {
		case "anvil":
			if(config.getBoolean("anvil.ban.banNotifier.notify")) {
				String s1 = Utils.chat(plugin.getConfig().getString("banMessageNotifier").replace("%player%", playerName));
				String s2 = Utils.chat(plugin.getConfig().getString("anvil.ban.banNotifier.notifyMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text));
				Broadcaster.bannedAlert(s1, s2, plugin, "antispam.ban.notify.anvil");
			}
			break;
		case "book":
			if(config.getBoolean("books.ban.banNotifier.notify")) {
				String s1 = Utils.chat(plugin.getConfig().getString("banMessageNotifier").replace("%player%", playerName));
				String s2 = Utils.chat(plugin.getConfig().getString("books.ban.banNotifier.notifyMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text));
				Broadcaster.bannedAlert(s1, s2, plugin, "antispam.ban.notify.book");
			}
			break;
		case "chat":
			if(config.getBoolean("chat.ban.banNotifier.notify")) {
				String s1 = Utils.chat(plugin.getConfig().getString("banMessageNotifier").replace("%player%", playerName));
				String s2 = Utils.chat(plugin.getConfig().getString("chat.ban.banNotifier.notifyMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text));
				Broadcaster.bannedAlert(s1, s2, plugin, "antispam.ban.notify.chat");
			}
			break;
		case "sign":
			if(config.getBoolean("signs.ban.banNotifier.notify")) {
				String s1 = Utils.chat(plugin.getConfig().getString("banMessageNotifier").replace("%player%", playerName));
				String s2 = Utils.chat(plugin.getConfig().getString("signs.ban.banNotifier.notifyMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text)).replace("\n", " ");
				Broadcaster.bannedAlert(s1, s2, plugin, "antispam.ban.notify.sign");
			}
			break;
		case "booktitle":
			if(config.getBoolean("booktitles.ban.banNotifier.notify")) {
				String s1 = Utils.chat(plugin.getConfig().getString("banMessageNotifier").replace("%player%", playerName));
				String s2 = Utils.chat(plugin.getConfig().getString("booktitles.ban.banNotifier.notifyMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text));
				Broadcaster.bannedAlert(s1, s2, plugin, "antispam.ban.notify.booktitle");
			}
			break;
		case "command":
			if(config.getBoolean("commands.ban.banNotifier.notify")) {
				String s1 = Utils.chat(plugin.getConfig().getString("banMessageNotifier").replace("%player%", playerName));
				String s2 = Utils.chat(plugin.getConfig().getString("commands.ban.banNotifier.notifyMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text));
				Broadcaster.bannedAlert(s1, s2, plugin, "antispam.ban.notify.command");
			}
			break;
		}
	}
}
