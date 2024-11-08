package com.lmlasmo.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lmlasmo.literalura.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	public Optional<Author> findByName(String name);
	
	@Query("SELECT a FROM Author a WHERE YEAR(a.birthYear) <= :ano AND (a.deathYear IS NULL || OR YEAR(a.deathYear) >= :ano)")
	public List<Author> findLivingAuthors(@Param("ano") int ano);
	
}
