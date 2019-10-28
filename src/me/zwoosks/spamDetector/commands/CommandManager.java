package me.zwoosks.spamDetector.commands;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import me.zwoosks.spamDetector.Main;
import me.zwoosks.spamDetector.utils.BanClearer;
import me.zwoosks.spamDetector.utils.BanLogGetter;
import me.zwoosks.spamDetector.utils.PlayerLookup;
import me.zwoosks.spamDetector.utils.Utils;

public class CommandManager implements CommandExecutor {
	
	private Main plugin;
	
	public CommandManager(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("author").setExecutor(this);
		plugin.getCommand("lookup").setExecutor(this);
		plugin.getCommand("antispam-unban").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("author")) {
			sender.sendMessage(Utils.chat("&dPlugin author: zwoosks"));
			sender.sendMessage(Utils.chat("&dYou can contact me via spigot or via mail (&ezwoosks@protonmail.com&d)"));
			return true;
		} else if(cmd.getName().equalsIgnoreCase("lookup")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(player.hasPermission("antispam.lookup")) {
					if(args.length > 0) {
						if(args[0].equalsIgnoreCase("ban")) {
							if(args.length == 2) {
								String name = args[1];
								if(plugin.getConfig().getBoolean("online")) {
									if(validUUID(name)) {
										String exists = getName(name);
										if(!exists.equals("error")) {
											boolean isBanned = PlayerLookup.isBanned(name, plugin);
											if(isBanned) {
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOnlineIsBanned").replace("%player%", player.getName()).replace("%uuid%", name).replace("%target%", getName(name))));
												String [] info = BanLogGetter.getBanLog(name, plugin);
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanIllegalWord").replace("%word%", info[1])));
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanIllegalText").replace("%text%", info[2])));
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanWhereDidHeWrite").replace("%place%", info[3])));
											} else {
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOnlineIsNotBanned").replace("%player%", player.getName()).replace("%uuid%", name).replace("%target%", getName(name))));
											}
										} else {
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupNoUUIDregistered").replace("%player%", player.getName()).replace("%uuid%", name)));
										}
									} else if(validUsername(name)) {
										String exists = getUuid(name);
										if(!exists.equals("error")) {
											String uuid = getUuid(name);
											boolean isBanned = PlayerLookup.isBanned(uuid, plugin);
											if(isBanned) {
												Bukkit.broadcastMessage(uuid);
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOnlineIsBanned").replace("%player%", player.getName()).replace("%uuid%", uuid).replace("%target%", name)));
												String [] info = BanLogGetter.getBanLog(uuid, plugin);
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanIllegalWord").replace("%word%", info[1])));
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanIllegalText").replace("%text%", info[2])));
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanWhereDidHeWrite").replace("%place%", info[3])));
											} else {
												player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOnlineIsNotBanned").replace("%player%", player.getName()).replace("%uuid%", uuid).replace("%target%", name)));
											}
										} else {
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupNoUsernameRegistered").replace("%player%", player.getName()).replace("%target%", name)));
										}
									} else {
										player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupIncorrectNameOrUUID").replace("%player%", player.getName())));
									}
								} else if(!plugin.getConfig().getBoolean("online")) {
									if(validUUID(name)) {
										player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOfflineNoUUID").replace("%player%", player.getName())));
									} else if(validUsername(name)) {
										boolean isBanned = PlayerLookup.isBanned(name, plugin);
										if(isBanned) {
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOfflineIsBanned").replace("%player%", player.getName()).replace("%target%", name)));
											String [] info = BanLogGetter.getBanLog(name.toLowerCase(), plugin);
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanIllegalWord").replace("%word%", info[1])));
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanIllegalText").replace("%text%", info[2])));
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupBanWhereDidHeWrite").replace("%place%", info[3])));
										} else {
											player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupOfflineIsNotBanned").replace("%player%", player.getName()).replace("%target%", name)));
										}
									} else {
										player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupIncorrectUsageOfflineBan").replace("%player%", player.getName())));
									}
								} else {
									player.sendMessage(Utils.chat("&cThe 'online' var from config.yml doesn't exist!"));
								}
							} else {
								player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupIncorrectUsageOnlineBan").replace("%player%", player.getName())));
							}
						}
					} else {
						player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupIncorrectUsage").replace("%player%", player.getName())));
					}
				} else {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("lookupNoPremission").replace("%player%", player.getName())));
				}
			} else {
				sender.sendMessage(Utils.chat(plugin.getConfig().getString("lookupYoureNotPlayer")));
			}
			return true;
		} else if(cmd.getName().equalsIgnoreCase("antispam-unban")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				String playerName = player.getName();
				if(player.hasPermission("antispam.unban")) {
					if(args.length == 1) {
						String name = args[0];
						if(plugin.getConfig().getBoolean("online")) {
							if(validUUID(name)) {
								String exists = getName(name);
								if(!exists.equals("error")) {
									boolean isBanned = PlayerLookup.isBanned(name, plugin);
									if(isBanned) {
										BanClearer.clearBan(name, plugin);
										BanClearer.clearBanLog(name, plugin);
										player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanOnlineSuccessfully").replace("%player%", player.getName()).replace("%uuid%", name).replace("%target%", getName(name))));
									} else {
										player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanOnlinePlayerIsNotBanned").replace("%player%", player.getName()).replace("%uuid%", name).replace("%target%", getName(name))));
									}
								} else {
									player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanNoUuidRegistered").replace("%player%", player.getName()).replace("%uuid%", name)));
								}
							} else if(validUsername(name)) {
								String exists = getUuid(name);
								if(!exists.equals("error")) {
									String uuid = getUuid(name);
									boolean isBanned = PlayerLookup.isBanned(uuid, plugin);
									if(isBanned) {
										BanClearer.clearBan(uuid, plugin);
										BanClearer.clearBanLog(uuid, plugin);
										player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanOnlineSuccessfully").replace("%player%", player.getName()).replace("%uuid%", uuid).replace("%target%", name)));
									} else {
										player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanOnlinePlayerIsNotBanned").replace("%player%", player.getName()).replace("%uuid%", uuid).replace("%target%", name)));
									}
								} else {
									player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanNoUsernameRegistered").replace("%player%", player.getName()).replace("%target%", name)));
								}
							} else {
								player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanIncorrectNameOrUuid").replace("%player%", player.getName())));
							}
						} else if(!plugin.getConfig().getBoolean("online")){
							if(validUUID(name)) {
								player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanOfflineModeNoUuidAccepted").replace("%player%", playerName)));
							} else if(validUsername(name)) {
								boolean isBanned = PlayerLookup.isBanned(name.toLowerCase(), plugin);
								if(isBanned) {
									try {
										BanClearer.clearBan(name.toLowerCase(), plugin);
										BanClearer.clearBanLog(name.toLowerCase(), plugin);
										player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanSuccesfully").replace("%player%", playerName).replace("%target%", name)));
									} catch(Exception ex) {
										player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanSomethingWentWrong").replace("%player%", playerName)));
										ex.printStackTrace();
									}
								} else {
									player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanPlayerIsNotBanned").replace("%player%", playerName).replace("%target%", name)));
								}
							} else {
								player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanInvalidUsername").replace("%player%", playerName)));
							}
						}
					} else {
						player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanCorrectUsage").replace("%player%", playerName)));
					}
				} else {
					player.sendMessage(Utils.chat(plugin.getConfig().getString("unbanNoPermission").replace("%player%", playerName)));
				}
			} else {
				sender.sendMessage(Utils.chat(plugin.getConfig().getString("unbanYoureNotPlayer")));
			}
		}
		return true;
	}
	
	private boolean validUUID(String UUID) {
		int len = UUID.length();
		if(len == 32) {
			return true;
		}
		return false;
	}
	
	private boolean validUsername(String username) {
		int len = username.length();
		if((len >= 3) && (len <= 16)) {
			return true;
		}
		return false;
	}
	
	private String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));           
            if(UUIDJson.isEmpty()) return "error";                       
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
       
        return "error";
    }
	
	private String getName(String uuid) {
        String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
        try {
            String nameJson = IOUtils.toString(new URL(url));           
            JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
            String playerSlot = nameValue.get(nameValue.size()-1).toString();
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
            return nameObject.get("name").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "error";
    }
}