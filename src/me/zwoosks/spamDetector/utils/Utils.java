package me.zwoosks.spamDetector.utils;

import org.bukkit.ChatColor;

public class Utils {
	
	public static String chars = "qwertyuiopasdfghjklñzxcvbnmçQWERTYUIOPASDFGHJKLÑZXCVBNM1234567890";
	
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String raw(String s) {
		return ChatColor.stripColor(s);
	}
	
	public static String replaceEx(String s) {
		String tractada = s;
		tractada = tractada.replace('á', 'a');
		tractada = tractada.replace('à', 'a');
		tractada = tractada.replace('é', 'e');
		tractada = tractada.replace('è', 'e');
		tractada = tractada.replace('í', 'i');
		tractada = tractada.replace('ì', 'i');
		tractada = tractada.replace('ó', 'o');
		tractada = tractada.replace('ò', 'o');
		tractada = tractada.replace('ú', 'u');
		tractada = tractada.replace('ù', 'u');
		tractada = tractada.replace('ç', 'c');
		tractada = tractada.replace('ñ', 'n');
		tractada = tractada.replace('ä', 'a');
		tractada = tractada.replace('ë', 'e');
		tractada = tractada.replace('ï', 'i');
		tractada = tractada.replace('ö', 'ö');
		tractada = tractada.replace('ü', 'ü');
		tractada = tractada.toLowerCase();
		return tractada.toLowerCase();
	}
}