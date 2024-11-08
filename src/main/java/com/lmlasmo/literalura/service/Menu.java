package com.lmlasmo.literalura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmlasmo.literalura.repository.AuthorRepository;
import com.lmlasmo.literalura.repository.BookRepository;

@Service
public class Menu {
	
	
	private ConsumeGuntendexAPI api;	
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;	
	
	@Autowired
	public Menu(BookRepository bookRepository, AuthorRepository authorRepository) {
		
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.api = new ConsumeGuntendexAPI(); 
		
	}
	
}
