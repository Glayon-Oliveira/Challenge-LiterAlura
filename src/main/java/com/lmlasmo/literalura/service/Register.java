package com.lmlasmo.literalura.service;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmlasmo.literalura.dto.AuthorDTO;
import com.lmlasmo.literalura.dto.BookDTO;
import com.lmlasmo.literalura.model.Author;
import com.lmlasmo.literalura.model.Book;
import com.lmlasmo.literalura.model.Language;
import com.lmlasmo.literalura.repository.AuthorRepository;
import com.lmlasmo.literalura.repository.BookRepository;
import com.lmlasmo.literalura.repository.LanguageRepository;

@Service
public class Register {
	
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	private LanguageRepository languageRepository;

	@Autowired
	public Register(BookRepository bookRepository, AuthorRepository authorRepository, LanguageRepository languageRepository) {

		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.languageRepository = languageRepository;		

	}
	
	public Book registeBook(BookDTO dto) {
		
		Optional<Book> bookRegisted = bookRepository.findByTitle(dto.getTitle());

		if (bookRegisted.isEmpty()) {
			
			Book book = new Book(dto);			
						
			book.setAuthors(registerAuthor(dto));
			book.setLanguages(registerLanguage(dto));	
			
			return bookRepository.save(book);

		}else {
			return bookRegisted.get();
		}
		
	}
	
	public Set<Language> registerLanguage(BookDTO dto) {				
		
		Set<Language> languages = dto.getLanguages().stream()							  
	  		     .map(l -> verifyNewLanguage(l))
	  		     .collect(Collectors.toSet());
		
		return languages;
		
	}
	
	public Language registerLanguage(String language) {				
		
		Language languageP = verifyNewLanguage(language);
		languageP = languageRepository.save(languageP);
		
		return languageP;
		
	}
	
	public Set<Author> registerAuthor(BookDTO dto) {
		
		Set<Author> authors = dto.getAuthors()
				 .stream()
				 .map(a -> registerAuthor(a))
				 .collect(Collectors.toSet());
		
		return authors;
		
	}
	
	public Author registerAuthor(AuthorDTO dto) {
		
		Author author = verifyNewAuthor(dto);
		author = authorRepository.save(author);
		
		return author;
		
	}
	
	private Language verifyNewLanguage(String language) {
		
		Optional<Language> registedLanguage = languageRepository.findByLanguage(language);
		
		if(registedLanguage.isPresent()) {
				return registedLanguage.get();
		}
		return new Language(language);
		
	}
	
	private Author verifyNewAuthor(AuthorDTO dto) {
		
		Optional<Author> registedAuthor = authorRepository.findByName(dto.getName());
		
		if(registedAuthor.isPresent()) {
				return registedAuthor.get();
		}
		return new Author(dto);
		
	}
	
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	public List<Author> getAllAuthors(){
		return authorRepository.findAll();
	}

	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public AuthorRepository getAuthorRepository() {
		return authorRepository;
	}

	public LanguageRepository getLanguageRepository() {
		return languageRepository;
	}	

}
