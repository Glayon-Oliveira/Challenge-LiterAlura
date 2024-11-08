package com.lmlasmo.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.literalura.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	public Optional<Book> findByTitle(String title);
	
	public List<Book> findByAuthorsName(String name);
	
	public List<Book> findByLanguagesContains(String language);

}
