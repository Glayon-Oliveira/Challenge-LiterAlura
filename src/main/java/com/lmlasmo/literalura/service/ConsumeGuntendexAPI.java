package com.lmlasmo.literalura.service;

import java.net.http.HttpClient;

public class ConsumeGuntendexAPI {
	
	private final String API_HOST = "https://gutendex.com/";
	private HttpClient client;
	
	public ConsumeGuntendexAPI() {
		
		client = HttpClient.newHttpClient();
		
	}	

}
