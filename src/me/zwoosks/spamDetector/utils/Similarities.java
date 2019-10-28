package me.zwoosks.spamDetector.utils;

import java.util.HashMap;

public class Similarities {
	
public static HashMap<String, Object> check(String s, String blockedWord) {
		
		HashMap<String, Object> toReturn = new HashMap<String, Object>();
		
		String tractada = "";
		for(char c : s.toCharArray()) {
			if(Utils.chars.contains(Character.toString(c))) {
				tractada = tractada + Character.toString(c);
			}
		}
		tractada = Utils.replaceEx(tractada);
		// Possible changes
		if(tractada.contains(blockedWord)) {
			toReturn.put("found", true);
			toReturn.put("word", blockedWord);
			toReturn.put("text", s);
		} else if(tractada.replace('3', 'e').replace('0', 'o').contains(blockedWord)) {
			toReturn.put("found", true);
			toReturn.put("word", blockedWord);
			toReturn.put("text", s);
		} else if(tractada.replace('3', 'e').replace('0', 'o').replace('v', 'b').contains(blockedWord)) {
			toReturn.put("found", true);
			toReturn.put("word", blockedWord);
			toReturn.put("text", s);
		} else if(tractada.replace('3', 'e').replace('0', 'o').replace('b', 'v').contains(blockedWord)) {
			toReturn.put("found", true);
			toReturn.put("word", blockedWord);
			toReturn.put("text", s);
		} else if(tractada.replace('3', 'e').replace('0', 'o').replace('b', 'v').replace('q', 'c').contains(blockedWord)) {
			toReturn.put("found", true);
			toReturn.put("word", blockedWord);
			toReturn.put("text", s);
		} else if(tractada.replace('3', 'e').replace('0', 'o').replace('v', 'b').replace('q', 'c').contains(blockedWord)) {
			toReturn.put("found", true);
			toReturn.put("word", blockedWord);
			toReturn.put("text", s);
		} else {
			toReturn.put("found", false);
		}
		
		return toReturn;
		
	}
	
}
