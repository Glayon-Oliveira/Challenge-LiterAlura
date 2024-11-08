package com.lmlasmo.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.literalura.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
