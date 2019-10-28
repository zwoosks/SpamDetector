package me.zwoosks.spamDetector.listeners.notifiedWords;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Cmd;
import me.zwoosks.spamDetector.listeners.notifiedWords.utils.Messages;
import me.zwoosks.spamDetector.utils.LogRegisterer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.Utils;

public class AlertCommandListener implements Listener {
	
	private Main plugin;
	
	public AlertCommandListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCommandAlert(PlayerCommandPreprocessEvent e) {
		if(!e.getPlayer().hasPermission("antispam.bypass.alert.command")) {
			if(plugin.getConfig().getBoolean("commands.alert.whitlelist")) {
				String[] cmd = e.getMessage().split(" ");
				String command = cmd[0];
				List<String> whitelistedCommands = plugin.getConfig().getStringList("commands.alert.whitelisted-commands");
				if(!whitelistedCommands.contains(command)) {
					String written = Utils.raw(e.getMessage());
					List<String> blockedWords = plugin.getConfig().getStringList("commands.alert.alertWords");
					for(String word : blockedWords) {
						HashMap<String, Object> map = Similarities.check(written, word);
						if((boolean)map.get("found") == true) {
							Player player = e.getPlayer();
							e.setCancelled(plugin.getConfig().getBoolean("commands.alert.cancelEventOnAlert"));
							Cmd.runCommand("commands", (String) map.get("word"), (String) map.get("text"), player.getName(), plugin.getConfig());
							Messages.sendToPlayer("commands", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
							Messages.alertStaff("commands", (String) map.get("word"), (String) map.get("text"), player, plugin.getConfig());
							if(plugin.getConfig().getBoolean("chat.alert.saveInLog")) {
								LogRegisterer.registerLog(player.getName(), (String) map.get("word"), (String) map.get("text"), "command", plugin);
							}
							
						} else if((boolean)map.get("found") == false) {} else {
							plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
									+ " Please, report the error!"));
						}
					}
				}
			}
		}
	}
	
}