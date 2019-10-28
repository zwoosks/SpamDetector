package me.zwoosks.spamDetector.listeners.bannedWords;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.utils.Banner;
import me.zwoosks.spamDetector.utils.BansLoggeer;
import me.zwoosks.spamDetector.utils.Similarities;
import me.zwoosks.spamDetector.utils.StaffNotifier;
import me.zwoosks.spamDetector.utils.Utils;

public class CommandListener implements Listener {
	
	private Main plugin;
	
	public CommandListener(Main plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(!e.getPlayer().hasPermission("antispam.bypass.ban.command")) {
			if(plugin.getConfig().getBoolean("commands.ban.whitlelist")) {
				String[] cmd = e.getMessage().split(" ");
				String command = cmd[0];
				List<String> whitelistedCommands = plugin.getConfig().getStringList("commands.ban.whitelisted-commands");
				if(!whitelistedCommands.contains(command)) {
					// Check if there's a banned word
					String written = Utils.raw(e.getMessage());
					List<String> blockedWords = plugin.getConfig().getStringList("commands.ban.bannedWords");
					
					for(String word : blockedWords) {
						HashMap<String, Object> map = Similarities.check(written, word);
						if((boolean)map.get("found") == true) {
							Player player = (Player) e.getPlayer();
							// Cancel the event
							e.setCancelled(plugin.getConfig().getBoolean("commands.ban.cancelEventOnPlayerBan"));
							// Ban the player
							if(plugin.getConfig().getBoolean("online")) {
								Banner.banUser(player.getUniqueId().toString(), "command", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
							} else {
								Banner.banUser(player.getName(), "command", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
							}
							// Notify
							StaffNotifier.notifyBanned(player.getName(), "command", (String) map.get("text"), (String) map.get("word"), plugin);
							//
							if(plugin.getConfig().getBoolean("online")) {
								String id = player.getUniqueId().toString().replace("-", "");
								BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "command", plugin);
							} else if(!plugin.getConfig().getBoolean("online")) {
								String id = player.getName();
								BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "command", plugin);
							}
						} else if((boolean)map.get("found") == false) {
							// Legal item, do nothing
						} else {
							plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
									+ " Please, report the error!"));
						}
					}
				}
			} else {
				// Check if there's a banned word
				String written = Utils.raw(e.getMessage());
				List<String> blockedWords = plugin.getConfig().getStringList("commands.ban.bannedWords");
				
				for(String word : blockedWords) {
					HashMap<String, Object> map = Similarities.check(written, word);
					if((boolean)map.get("found") == true) {
						Player player = (Player) e.getPlayer();
						// Cancel the event
						e.setCancelled(plugin.getConfig().getBoolean("commands.ban.cancelEventOnPlayerBan"));
						// Ban the player
						if(plugin.getConfig().getBoolean("online")) {
							Banner.banUser(player.getUniqueId().toString(), "command", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
						} else {
							Banner.banUser(player.getName(), "command", (String) map.get("text"), (String) map.get("word"), plugin, player.getName());
						}
						// Notify
						StaffNotifier.notifyBanned(player.getName(), "command", (String) map.get("text"), (String) map.get("word"), plugin);
						//
						if(plugin.getConfig().getBoolean("online")) {
							String id = player.getUniqueId().toString().replace("-", "");
							BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "command", plugin);
						} else if(!plugin.getConfig().getBoolean("online")) {
							String id = player.getName();
							BansLoggeer.logBan(id, (String) map.get("word"), (String) map.get("text"), "command", plugin);
						}
					} else if((boolean)map.get("found") == false) {
						// Legal item, do nothing
					} else {
						plugin.getLogger().info(Utils.chat("&c&lAn error occured while trying to check if a witten message is illegal."
								+ " Please, report the error!"));
					}
				}
			}
		}
	}
	
}
