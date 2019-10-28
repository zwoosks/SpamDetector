package me.zwoosks.spamDetector.listeners.notifiedWords;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Cmd;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Messages;
import me.zwoosks.spamDetector.utils.LogRegisterer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.Utils;

public class AlertSignListener implements Listener {
	
	private Main plugin;
	
	public AlertSignListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerUpdateSignAlert(SignChangeEvent e) {
		if(!e.getPlayer().hasPermission("antispam.bypass.alert.sign")) {
			String written = Utils.raw(e.getLine(0) + " " + e.getLine(1) + " " + e.getLine(2) + " " + e.getLine(3));
			List<String> blockedWords = plugin.getConfig().getStringList("signs.alert.alertWords");
			for(String word : blockedWords) {
				HashMap<String, Object> map = Similarities.check(written, word);
				if((boolean)map.get("found") == true) {
					Player player = e.getPlayer();
					e.setCancelled(plugin.getConfig().getBoolean("signs.alert.cancelEventOnAlert"));
					Cmd.runCommand("signs", (String) map.get("word"), (String) map.get("text"), player.getName(), plugin.getConfig());
					Messages.sendToPlayer("signs", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
					Messages.alertStaff("signs", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
					if(plugin.getConfig().getBoolean("signs.alert.saveInLog")) {
						LogRegisterer.registerLog(player.getName(), (String) map.get("word"), (String) map.get("text"), "sign", plugin);
					}
					
				} else if((boolean)map.get("found") == false) {} else {
					plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
							+ " Please, report the error!"));
				}
			}
		}
	}
	
}