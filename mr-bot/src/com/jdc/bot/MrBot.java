package com.jdc.bot;

import java.util.HashMap;
import java.util.Map;

public class MrBot {

	private Map<String, String> memory;
	private String last;
	
	public MrBot(){
		memory = new HashMap<>();
		memory.put("name", "I am Mr. Bot!");
		memory.put("hi", "Hello");
		memory.put("how are you", "I'm fine thank you.");
	}

	public String talk(String message) {
		
		if(null != last) {
			// teaching
			memory.put(last.toLowerCase(), message);
			last = null;
			return "Thank you";
		}
		
		// search message from memory
		String answer = memory.get(message.toLowerCase());
		
		if(null != answer) {
			// if found -> return answer
			return answer;
		} 
		
		// not found -> return please teach me
		last = message;
		
		return "Please teach me!";
	}
}
