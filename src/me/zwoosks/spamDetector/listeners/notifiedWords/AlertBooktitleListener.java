package me.zwoosks.spamDetector.listeners.notifiedWords;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Cmd;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Messages;
import me.zwoosks.spamDetector.utils.LogRegisterer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.Utils;

public class AlertBooktitleListener implements Listener {
	
	private Main plugin;
	
	public AlertBooktitleListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBookTitleEditAlert(PlayerEditBookEvent e) {
		if(!e.getPlayer().hasPermission("antispam.bypass.alert.booktitle")) {
			try {
				BookMeta meta = e.getNewBookMeta();
				String written = meta.getTitle();
				List<String> blockedWords = plugin.getConfig().getStringList("booktitles.alert.alertWords");
				for(String word: blockedWords) {
					HashMap<String, Object> map = Similarities.check(written, word);
					if((boolean)map.get("found") == true) {
						Player player = e.getPlayer();
						e.setCancelled(plugin.getConfig().getBoolean("booktitles.alert.cancelEventOnAlert"));
						Cmd.runCommand("booktitles", (String) map.get("word"), (String) map.get("text"), player.getName(), plugin.getConfig());
						Messages.sendToPlayer("booktitles", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
						Messages.alertStaff("booktitles", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
						if(plugin.getConfig().getBoolean("booktitles.alert.saveInLog")) {
							LogRegisterer.registerLog(player.getName(), (String) map.get("word"), (String) map.get("text"), "booktitle", plugin);
						}
					} else if((boolean)map.get("found") == false) {
						// Legal item, do nothing
					} else {
						plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
							+ " Please, report the error!"));
					}
				}
			} catch(Exception ex) {}
		}
	}
}