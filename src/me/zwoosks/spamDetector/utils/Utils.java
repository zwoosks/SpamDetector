package me.zwoosks.spamDetector.utils;

import org.bukkit.ChatColor;

public class Utils {
	
	public static String chars = "qwertyuiopasdfghjkl�zxcvbnm�QWERTYUIOPASDFGHJKL�ZXCVBNM1234567890";
	
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String raw(String s) {
		return ChatColor.stripColor(s);
	}
	
	public static String replaceEx(String s) {
		String tractada = s;
		tractada = tractada.replace('�', 'a');
		tractada = tractada.replace('�', 'a');
		tractada = tractada.replace('�', 'e');
		tractada = tractada.replace('�', 'e');
		tractada = tractada.replace('�', 'i');
		tractada = tractada.replace('�', 'i');
		tractada = tractada.replace('�', 'o');
		tractada = tractada.replace('�', 'o');
		tractada = tractada.replace('�', 'u');
		tractada = tractada.replace('�', 'u');
		tractada = tractada.replace('�', 'c');
		tractada = tractada.replace('�', 'n');
		tractada = tractada.replace('�', 'a');
		tractada = tractada.replace('�', 'e');
		tractada = tractada.replace('�', 'i');
		tractada = tractada.replace('�', '�');
		tractada = tractada.replace('�', '�');
		tractada = tractada.toLowerCase();
		return tractada.toLowerCase();
	}
}