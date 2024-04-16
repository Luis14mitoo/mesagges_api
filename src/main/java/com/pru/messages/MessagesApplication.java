package com.pru.messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.pru.messages.util.JsonQueryIA;

@SpringBootApplication
public class MessagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagesApplication.class, args);
	}
	
	
	@Bean
	public RestTemplate resttemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public JsonQueryIA jsonUtil() {
		return  new  JsonQueryIA();		
		
	}
	
	
	

}
