package com.lmlasmo.literalura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.literalura.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long>{
	
	public Optional<Language> findByLanguage(String language);	

}
