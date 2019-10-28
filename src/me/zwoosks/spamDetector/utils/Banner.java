package me.zwoosks.spamDetector.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import me.zwoosks.spamDetector.Main;

public class Banner {
	
	@SuppressWarnings("deprecation")
	public static void banUser(String playerName, String whereDidHeWrite, String text, String illegalWord, Main plugin, String name) {
		
		FileConfiguration config = plugin.getConfig();
		
		if(whereDidHeWrite.equals("anvil")) {
			if(config.getBoolean("anvil.ban.banPlayer")) {
				String s1 = Utils.chat(config.getString("beenBannedMessage"));
				String s2 = Utils.chat(config.getString("anvil.ban.reasonBanMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%br%", "\n").replace("%player%", name));
				String total = s1 + "\n" + s2;
				BanRegisterer.registerBan(total, playerName.replace("-", ""), plugin);
				if(plugin.getConfig().getBoolean("online")) {
					Bukkit.getServer().getPlayer(UUID.fromString(playerName)).kickPlayer(total);
				} else {
					Bukkit.getServer().getPlayer(playerName).kickPlayer(total);
				}
			}
			if(config.getBoolean("anvil.ban.runCommand")) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = config.getString("anvil.ban.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", name);
				Bukkit.getServer().dispatchCommand(console, command);
			}
		} else if(whereDidHeWrite.equals("book")) {
			if(config.getBoolean("books.ban.banPlayer")) {
				String s1 = Utils.chat(config.getString("beenBannedMessage"));
				String s2 = Utils.chat(config.getString("books.ban.reasonBanMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%br%", "\n").replace("%player%", name));
				String total = s1 + "\n" + s2.replace("\n", " ");
				BanRegisterer.registerBan(total, playerName.replace("-", ""), plugin);
				if(plugin.getConfig().getBoolean("online")) {
					Bukkit.getServer().getPlayer(UUID.fromString(playerName)).kickPlayer(total);
				} else {
					Bukkit.getServer().getPlayer(playerName).kickPlayer(total);
				}
			}
			if(config.getBoolean("books.ban.runCommand")) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = config.getString("books.ban.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", name);
				Bukkit.getServer().dispatchCommand(console, command);
			}
		} else if(whereDidHeWrite.equals("chat")) {
			if(config.getBoolean("chat.ban.banPlayer")) {
				String s1 = Utils.chat(config.getString("beenBannedMessage"));
				String s2 = Utils.chat(config.getString("chat.ban.reasonBanMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%br%", "\n").replace("%player%", name));
				String total = s1 + "\n" + s2;
				BanRegisterer.registerBan(total, playerName.replace("-", ""), plugin);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				    public void run() {
				    	if(plugin.getConfig().getBoolean("online")) {
							Bukkit.getServer().getPlayer(UUID.fromString(playerName)).kickPlayer(total);
						} else {
							Bukkit.getServer().getPlayer(playerName).kickPlayer(total);
						}
				    }
				}, 2L);
			}
			if(config.getBoolean("chat.ban.runCommand")) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = config.getString("chat.ban.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", name);
				Bukkit.getServer().dispatchCommand(console, command);
			}
		} else if(whereDidHeWrite.equals("sign")) {
			if(config.getBoolean("signs.ban.banPlayer")) {
				String s1 = Utils.chat(config.getString("beenBannedMessage"));
				String s2 = Utils.chat(config.getString("signs.ban.reasonBanMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%br%", "\n").replace("%player%", name));
				String total = s1 + "\n" + s2;
				BanRegisterer.registerBan(total, playerName.replace("-", ""), plugin);
				if(playerName.length() == 36) {
					Bukkit.getServer().getPlayer(UUID.fromString(playerName)).kickPlayer(total);
				} else {
					Bukkit.getServer().getPlayer(playerName).kickPlayer(total);
				}
			}
			if(config.getBoolean("signs.ban.runCommand")) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = config.getString("signs.ban.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", name);
				Bukkit.getServer().dispatchCommand(console, command);
			}
		} else if(whereDidHeWrite.equals("booktitle")) {
			if(config.getBoolean("booktitles.ban.banPlayer")) {
				String s1 = Utils.chat(config.getString("beenBannedMessage"));
				String s2 = Utils.chat(config.getString("booktitles.ban.reasonBanMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%br%", "\n").replace("%player%", name));
				String total = s1 + "\n" + s2.replace("\n", " ");
				BanRegisterer.registerBan(total, playerName.replace("-", ""), plugin);
				if(plugin.getConfig().getBoolean("online")) {
					Bukkit.getServer().getPlayer(UUID.fromString(playerName)).kickPlayer(total);
				} else {
					Bukkit.getServer().getPlayer(playerName).kickPlayer(total);
				}
			}
			if(config.getBoolean("books.ban.runCommand")) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = config.getString("books.ban.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", name);
				Bukkit.getServer().dispatchCommand(console, command);
			}
		} else if(whereDidHeWrite.equals("command")) {
			if(config.getBoolean("commands.ban.banPlayer")) {
				String s1 = Utils.chat(config.getString("beenBannedMessage"));
				String s2 = Utils.chat(config.getString("commands.ban.reasonBanMessage").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%br%", "\n").replace("%player%", name));
				String total = s1 + "\n" + s2;
				BanRegisterer.registerBan(total, playerName.replace("-", ""), plugin);
				if(plugin.getConfig().getBoolean("online")) {
					Bukkit.getServer().getPlayer(UUID.fromString(playerName)).kickPlayer(total);
				} else {
					Bukkit.getServer().getPlayer(playerName).kickPlayer(total);
				}
			}
			if(config.getBoolean("commands.ban.runCommand")) {
				ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
				String command = config.getString("commands.ban.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", name);
				Bukkit.getServer().dispatchCommand(console, command);
			}
		}
	}
}