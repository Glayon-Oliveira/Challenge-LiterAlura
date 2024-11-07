package com.lmlasmo.literalura.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lmlasmo.literalura.dto.AuthorDTO;
import com.lmlasmo.literalura.dto.BookDTO;
import com.lmlasmo.literalura.dto.ResultDTO;

public class ConsumeGuntendexAPI {
	
	private final String API_HOST = "https://gutendex.com/";
	private HttpClient client;
	
	public ConsumeGuntendexAPI() {
		
		client = HttpClient.newHttpClient();
		
	}	
	
	public List<BookDTO> requestSearch(String search) {
		
		List<BookDTO> bookList = new ArrayList<BookDTO>();				
		
		try {
			
			search = URLEncoder.encode(search, "UTF-8");
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(new URI(String.join("", this.API_HOST, "books/?search=", search)))
					.GET()
					.build();
			
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() == 200) {
				
				String jsonResponse = response.body();
				
				JsonMapper mapper = new JsonMapper();
				
				ResultDTO result = mapper.readValue(jsonResponse, ResultDTO.class);
				
				if(result.getCount() > 0) {
					
					bookList.addAll(result.getResults());
					
					Optional<String> next = Optional.ofNullable(result.getNext());
					Optional<String> previous = Optional.ofNullable(result.getPrevious());										
					
					while(next.isPresent()) {
						
						request = HttpRequest.newBuilder()
								.uri(new URI(next.get()))
								.GET()
								.build();
						
						response = client.send(request, HttpResponse.BodyHandlers.ofString());
						
						if(response.statusCode() == 200) {
							
							String jsonNextResposta = response.body();
							
							ResultDTO nextResult = mapper.readValue(jsonNextResposta, ResultDTO.class);
							
							bookList.addAll(nextResult.getResults());
							
							next = Optional.ofNullable(nextResult.getNext());
							
						}else break;										
						
					}
					
				}								
				
			}
			
		}catch(Exception e) {
			System.out.println("Erro de requisição");
			e.printStackTrace();		
		}			
		
		return bookList;
		
	}		

}
