package me.zwoosks.spamDetector;

import org.bukkit.plugin.java.JavaPlugin;

import me.zwoosks.spamDetector.commands.CommandManager;
import me.zwoosks.spamDetector.listeners.bannedWords.AnvilListener;
import me.zwoosks.spamDetector.listeners.bannedWords.BookListener;
import me.zwoosks.spamDetector.listeners.bannedWords.BookTitleListener;
import me.zwoosks.spamDetector.listeners.bannedWords.ChatListener;
import me.zwoosks.spamDetector.listeners.bannedWords.CommandListener;
import me.zwoosks.spamDetector.listeners.bannedWords.JoinListener;
import me.zwoosks.spamDetector.listeners.bannedWords.SignListener;
import me.zwoosks.spamDetector.listeners.notifiedWords.AlertAnvilListener;
import me.zwoosks.spamDetector.listeners.notifiedWords.AlertBookListener;
import me.zwoosks.spamDetector.listeners.notifiedWords.AlertBooktitleListener;
import me.zwoosks.spamDetector.listeners.notifiedWords.AlertChatListener;
import me.zwoosks.spamDetector.listeners.notifiedWords.AlertCommandListener;
import me.zwoosks.spamDetector.listeners.notifiedWords.AlertSignListener;
import me.zwoosks.spamDetector.mySQL.TableCreators;

public class Main extends JavaPlugin {
	
	/*
	 * Plugin made by Zwoosks (zwoosks@protonmail.com)
	 * Do not redistribute or edit this plugin.
	 */
	
	@Override
	public void onEnable() {
		new CommandManager(this);
		new JoinListener(this);
		
		if(this.getConfig().getBoolean("anvil.ban.active")) new AnvilListener(this);
		if(this.getConfig().getBoolean("chat.ban.active")) new ChatListener(this);
		if(this.getConfig().getBoolean("signs.ban.active")) new SignListener(this);
		if(this.getConfig().getBoolean("commands.ban.active")) new CommandListener(this);
		if(this.getConfig().getBoolean("books.ban.active")) new BookListener(this);
		if(this.getConfig().getBoolean("booktitles.ban.active")) new BookTitleListener(this);
		
		if(this.getConfig().getBoolean("chat.alert.active")) new AlertChatListener(this);
		if(this.getConfig().getBoolean("anvil.alert.active")) new AlertAnvilListener(this);
		if(this.getConfig().getBoolean("signs.alert.active")) new AlertSignListener(this);
		if(this.getConfig().getBoolean("books.alert.active")) new AlertBookListener(this);
		if(this.getConfig().getBoolean("booktitles.alert.active")) new AlertBooktitleListener(this);
		if(this.getConfig().getBoolean("commands.alert.active")) new AlertCommandListener(this);
		saveDefaultConfig();
		TableCreators tableCreators = new TableCreators(this);
		tableCreators.createTables();
		this.getLogger().info("(!) SpamDetector plugin loaded successfully (!)");
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("(!) SpamDetector in being disabled (!)");
	}
}
