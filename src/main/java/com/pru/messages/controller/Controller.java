package com.pru.messages.controller;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.pru.messages.util.JsonQueryIA;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;


//@CrossOrigin(origins = "http://localhost:4200/"

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class Controller {



	@Autowired
	private JsonQueryIA jsonUtil = new JsonQueryIA();

	@Autowired
	private RestTemplate restTemplate;

	@Value("${url}")
	private String url;
	@Value("${my.key}")
	private String key;

	private String result;
	private String resultQuery;
	  
	

	
	
	@GetMapping("hello")
	public String Hola() {
				
		return "Hello world";
	}
	
	
	

	
	@PostMapping("/message")
    public  ResponseEntity<String>  consulta(@RequestBody JSONObject devices){
		
		 String res = "";
		String queryIA="De la lista de palabras que te voy a pasar ordenalas de manera logica y "
				+ "determina si  con esas palabras puedes extraer"
				+ "una oracion o un mensaje  en castellano, solo respode con la oracion  de lo contrario responde con un 1: ";
		
		result="";
		
		String response ="";
	
		String d = devices.toJSONString();
		
		ArrayList<String> Lista = new  ArrayList<>();
		try {
			JSONObject j  = (JSONObject) (new JSONParser()).parse(d);
			JSONArray j1 = (JSONArray) j.get("devices");
			
			for(int i =0; i<j1.size();i++) {
				
				JSONObject content = (JSONObject) j1.get(i);
				JSONArray content1 = (JSONArray) content.get("message");
				
				for(int x = 0; x<content1.size();x++) {
					
					if(content1.get(x) != "") {
						Lista.add(content1.get(x).toString());
					}					
				}			
			}			
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		
		Lista.forEach( element -> result += element +";");
		
		response = result;
		
		if(response.length() >50) {
			return ResponseEntity.badRequest().body("Very long parameters");
		}
		
		queryIA= queryIA+result;
		
		
        res = queryIA(queryIA);       
       
        
        
        if(res.equals("1")) {        	
        	return ResponseEntity.badRequest().body("Invalid request parameters");
        }else {
        	return ResponseEntity.ok(res);
        }            
        
    }


	  
	  

	
	@PostMapping("/message_split")
	public  ResponseEntity<String>  messageSplit(@RequestBody JSONObject devices){
	
	resultQuery ="";
	String res = "";
	String queryIA="De la lista de palabras que te voy a pasar ordenalas de manera logica y "
	+ "determina si  con esas palabras puedes extraer"
	+ "una oracion o un mensaje  en castellano, solo respode con la oracion  de lo contrario responde con un 1: ";
	String response ="";
	String d = devices.toJSONString();
	ArrayList<String> Listat = new  ArrayList<>();
	JSONObject j;
	
	try {
		j = (JSONObject) (new JSONParser()).parse(d);
		JSONArray j1 = (JSONArray) j.get("message");		
		for(int x = 0; x<j1.size();x++) {
			if(j1.get(x) != "") {
				Listat.add(j1.get(x).toString());
			}					
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	Listat.forEach( element -> {resultQuery += element +";";});
	
	response = resultQuery;
		
	if(response.length() >50 || response.length() < 3 ) {
		return ResponseEntity.badRequest().body("Very long parameters");
	}
	
	queryIA= queryIA+response;
	
    res = queryIA(queryIA);	
	
	  if(res.equals("1")) { return
	  ResponseEntity.badRequest().body("Invalid request parameters"); }else {
	  return ResponseEntity.ok(res); }	
	
	}
		
	 
	  

	  
	  @GetMapping("/t1")
	  public String test() {
		 return  queryIA("en 10 palabras que es el cosmo");
	  }
	  
	  
	
	  
	  
	  private String queryIA(String query) {
		  
				JSONObject text = new JSONObject();
				text = jsonUtil.creaJson(query);
				
				// Crea los headers
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.APPLICATION_JSON);

			    // Realiza la petición POST
			    ResponseEntity<JSONObject> jsonObjectt = restTemplate.exchange(url+key, HttpMethod.POST, new HttpEntity<>(text, headers), JSONObject.class);
			    
			    //Se procesa el JSON de respuesta para extater el texto de respuesta
			    JSONObject  result = jsonObjectt.getBody();
			    String res1 = result.toString();
			    String response ="0";
				try {
					JSONObject jsonObject  = (JSONObject) (new JSONParser()).parse(res1);
					JSONArray candidates = (JSONArray) jsonObject.get("candidates");
					JSONObject content = (JSONObject) candidates.get(0);
					JSONObject contentt = (JSONObject) content.get("content");
					JSONArray parts = (JSONArray) contentt.get("parts");
					JSONObject text1 = (JSONObject) parts.get(0);
					Object textValue = text1.get("text");
					response = textValue.toString();
				} catch (ParseException e) {
					e.printStackTrace();
				}			   
		  
		  return response;		
		  
		  
	  }
	  
	  
	  
	  
    
    
}
