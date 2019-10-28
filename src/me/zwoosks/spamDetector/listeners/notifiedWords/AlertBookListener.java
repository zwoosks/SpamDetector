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

public class AlertBookListener implements Listener {
	
	private Main plugin;
	
	public AlertBookListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerEditBookAlert(PlayerEditBookEvent e) {
		
		if(!e.getPlayer().hasPermission("antispam.bypass.alert.book")) {
			BookMeta meta = e.getNewBookMeta();
			int pages = meta.getPageCount();
			for(int i = 1; i <= pages; i++) {
				String written = Utils.raw(meta.getPage(i));
				List<String> blockedWords = plugin.getConfig().getStringList("books.alert.alertWords");
				for(String word : blockedWords) {
					HashMap<String, Object> map = Similarities.check(written, word);
					if((boolean) map.get("found")) {
						Player player = e.getPlayer();
						e.setCancelled(plugin.getConfig().getBoolean("books.alert.cancelEventOnAlert"));
						Cmd.runCommand("books", (String) map.get("word"), (String) map.get("text"), player.getName(), plugin.getConfig());
						Messages.sendToPlayer("books", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
						Messages.alertStaff("books", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
						if(plugin.getConfig().getBoolean("books.alert.saveInLog")) {
							LogRegisterer.registerLog(player.getName(), (String) map.get("word"), (String) map.get("text"), "book", plugin);
						}
					} else if((boolean) map.get("found") == false) {
						// Nothing
					} else {
						plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
						+ " Please, report the error!"));
					}
				}
			}
		}
	}
}