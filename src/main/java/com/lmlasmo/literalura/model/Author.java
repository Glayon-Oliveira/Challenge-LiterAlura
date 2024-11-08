package com.lmlasmo.literalura.model;

import java.util.HashSet;
import java.util.Set;

import com.lmlasmo.literalura.dto.AuthorDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "birth_year")
	private int birthYear;
	
	@Column(name = "death_year")
	private int deathYear;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "book_author",
			   joinColumns = @JoinColumn(name = "author_id"),
			   inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<Book> books = new HashSet<Book>();
	
	public Author() {}
	
	public Author(AuthorDTO dto) {
		
		this.name = dto.getName();
		this.birthYear = dto.getBirthYear();
		this.deathYear = dto.getDeathYear();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getDeathYear() {
		return deathYear;
	}

	public void setDeathYear(int deathYear) {
		this.deathYear = deathYear;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}	
	
}
