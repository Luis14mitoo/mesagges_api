package com.pru.messages.util;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;



@Component
public class JsonQueryIA {
	
	
	
	
	public JSONObject creaJson(String texto) {
		
		JSONObject text = new JSONObject();
		text.put("text", texto);
		
			JSONObject[] parts1 = new JSONObject[1];
			parts1[0] = text;
		
		JSONObject parts = new JSONObject();
		parts.put("parts", parts1);
		
			JSONObject[] contents1 = new JSONObject[1];
			contents1[0] = parts;		
		
		JSONObject contents = new JSONObject();
		contents.put("contents", contents1);
		
		return contents;
	}

}
