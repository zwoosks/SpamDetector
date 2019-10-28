package me.zwoosks.spamDetector.listeners.notifiedWords;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Cmd;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Messages;
import me.zwoosks.spamDetector.utils.LogRegisterer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.Utils;

public class AlertAnvilListener implements Listener {
	
	private Main plugin;
	
	public AlertAnvilListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onItemRenameAnvil(InventoryClickEvent e) {
		try {
			if(e.getSlotType() == InventoryType.SlotType.RESULT) {
				if(!e.getWhoClicked().hasPermission("antispam.bypass.alert.anvil")) {
					String written = Utils.raw(e.getCurrentItem().getItemMeta().getDisplayName());
					List<String> blockedWords = plugin.getConfig().getStringList("anvil.alert.alertWords");
					for(String word : blockedWords) {
						HashMap<String, Object> map = Similarities.check(written, word);
						if((boolean)map.get("found") == true) {
							Player player = (Player) e.getWhoClicked();
							e.setCancelled(plugin.getConfig().getBoolean("anvil.alert.cancelEventOnAlert"));
							Cmd.runCommand("anvil", (String) map.get("word"), (String) map.get("text"), player.getName(), plugin.getConfig());
							Messages.sendToPlayer("anvil", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
							Messages.alertStaff("anvil", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
							if(plugin.getConfig().getBoolean("anvil.alert.saveInLog")) {
								LogRegisterer.registerLog(player.getName(), (String) map.get("word"), (String) map.get("text"), "anvil", plugin);
							}
							
						} else if((boolean)map.get("found") == false) {} else {
							plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
									+ " Please, report the error!"));
						}
					}
				}
			}
		} catch(Exception ex) {}
	}
	
}