package me.zwoosks.spamDetector.listeners.notifiedWords.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Cmd {
	
	public static void runCommand(String whereDidHeWrite, String illegalWord, String text, String playerName, FileConfiguration config) {
		if(config.getBoolean(whereDidHeWrite + ".alert.runCommand")) {
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			String command = config.getString(whereDidHeWrite + ".alert.dispatchedCommand").replace("%illegalWord%", illegalWord).replace("%illegalText%", text).replace("%player%", playerName);
			Bukkit.getServer().dispatchCommand(console, command);
		}
	}
}