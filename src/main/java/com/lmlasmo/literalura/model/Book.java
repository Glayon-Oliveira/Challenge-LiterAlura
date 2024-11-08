package com.lmlasmo.literalura.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "languages")
	private String languages;
	
	@Column(name = "download_count")
	private long downloadCount;
	
	@ManyToMany(mappedBy = "books")	
	private Set<Author> authors = new HashSet<Author>();
	
	public Book() {}

}
