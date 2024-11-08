package com.lmlasmo.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.literalura.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	public Book findByTitle(String title);
	
	public List<Book> findByAuthorName(String name);
	
	public List<Book> findByLanguagesContains(String language);

}
