package me.zwoosks.spamDetector.listeners.bannedWords;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.utils.Banner;
import me.zwoosks.spamDetector.utils.BansLoggeer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.StaffNotifier;
import me.zwoosks.spamDetector.utils.Utils;

public class AnvilListener implements Listener {
	
	private Main plugin;
	
	public AnvilListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		try {
			if(e.getSlotType() == InventoryType.SlotType.RESULT) {
				if(!e.getWhoClicked().hasPermission("antispam.bypass.ban.anvil")) {
					String name = Utils.raw(e.getCurrentItem().getItemMeta().getDisplayName());
					// Revisar si conté alguna paraula prohibida
					List<String> blockedWords = plugin.getConfig().getStringList("anvil.ban.bannedWords");
					for(String word : blockedWords) {
						HashMap<String, Object> map = Similarities.check(name, word);
						if((boolean)map.get("found") == true) {
							Player player = (Player) e.getWhoClicked();
							// Ban the player
							if(plugin.getConfig().getBoolean("online")) {
								Banner.banUser(player.getUniqueId().toString(), "anvil", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
							} else {
								Banner.banUser(player.getName(), "anvil", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
							}
							// Cancel the event
							e.setCancelled(plugin.getConfig().getBoolean("anvil.ban.cancelEventOnPlayerBan"));
							// Notify
							StaffNotifier.notifyBanned(player.getName(), "anvil", (String) map.get("text"), (String) map.get("word"), plugin);
							// Register ban
							if(plugin.getConfig().getBoolean("online")) {
								String id = player.getUniqueId().toString().replace("-", "");
								BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "anvil", plugin);
							} else if(!plugin.getConfig().getBoolean("online")) {
								String id = player.getName();
								BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "anvil", plugin);
							}
						} else if((boolean)map.get("found") == false) {
							// Legal item, do nothing
						} else {
							plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if the item name of an anvil is illegal."
									+ " Please, report the error!"));
						}
					}
				}
			}
		} catch(Exception ex) {}
	}
	
}