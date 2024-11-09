package com.lmlasmo.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmlasmo.literalura.model.Language;

public interface LanguageRepository extends JpaRepository<Language, Long>{
	
	public Language findByLanguage(String language);

}
