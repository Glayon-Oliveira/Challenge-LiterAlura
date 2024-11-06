package com.lmlasmo.literalura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorDTO {

	@JsonProperty("name")
	private String name;

	@JsonProperty("birth_year")
	private int birthYear;

	@JsonProperty("death_year")
	private int deathYear;

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

}
