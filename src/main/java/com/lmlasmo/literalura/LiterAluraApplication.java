package com.lmlasmo.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lmlasmo.literalura.service.Menu;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner{
	
	private Menu menu;
	
	@Autowired
	public LiterAluraApplication(Menu menu) {
		this.menu = menu;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		menu.showMenu();
		
	}

}
