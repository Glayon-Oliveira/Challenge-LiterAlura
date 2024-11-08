package com.lmlasmo.literalura.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.lmlasmo.literalura.dto.BookDTO;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "book_languages",
			joinColumns = @JoinColumn(name = "book_id")
			)
	@Column(name = "language")
	private List<String> languages = new ArrayList<String>();
	
	@Column(name = "download_count")
	private long downloadCount;
	
	@ManyToMany(mappedBy = "books")	
	private Set<Author> authors = new HashSet<Author>();
	
	public Book() {}
	
	public Book(BookDTO book) {
		
		this.title = book.getTitle();
		this.downloadCount = book.getDownloadCount();
		this.languages = book.getLanguages();	
		
		this.authors = book.getAuthors().stream()
							.map(a -> new Author(a))
							.collect(Collectors.toSet());
					
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public long getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(long downloadCount) {
		this.downloadCount = downloadCount;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}	

}
