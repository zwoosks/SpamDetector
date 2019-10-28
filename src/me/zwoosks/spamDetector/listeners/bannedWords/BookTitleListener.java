package me.zwoosks.spamDetector.listeners.bannedWords;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.utils.Banner;
import me.zwoosks.spamDetector.utils.BansLoggeer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.StaffNotifier;
import me.zwoosks.spamDetector.utils.Utils;

public class BookTitleListener implements Listener {
	
	private Main plugin;
	
	public BookTitleListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerBookEditBan(PlayerEditBookEvent e) {
		if(!e.getPlayer().hasPermission("antispam.bypass.ban.book")) {
			try {
				BookMeta meta = e.getNewBookMeta();
				String written = meta.getTitle();
				List<String> blockedWords = plugin.getConfig().getStringList("booktitles.ban.bannedWords");
				for(String word : blockedWords) {
					HashMap<String, Object> map = Similarities.check(written, word);
					if((boolean)map.get("found") == true) {
						Player player = (Player) e.getPlayer();
						// Cancel the event
						e.setCancelled(plugin.getConfig().getBoolean("booktitles.ban.cancelEventOnPlayerBan"));
						// Ban the player
						if(plugin.getConfig().getBoolean("online")) {
							Banner.banUser(player.getUniqueId().toString(), "booktitle", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
						} else {
							Banner.banUser(player.getName(), "booktitle", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
						}
						// Notify
						StaffNotifier.notifyBanned(player.getName(), "booktitle", (String) map.get("text"), (String) map.get("word"), plugin);
						//
						if(plugin.getConfig().getBoolean("online")) {
							String id = player.getUniqueId().toString().replace("-", "");
							BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "booktitle", plugin);
						} else if(!plugin.getConfig().getBoolean("online")) {
							String id = player.getName();
							BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "booktitle", plugin);
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