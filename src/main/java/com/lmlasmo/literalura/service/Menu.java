package com.lmlasmo.literalura.service;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmlasmo.literalura.dto.BookDTO;
import com.lmlasmo.literalura.model.Author;
import com.lmlasmo.literalura.model.Book;
import com.lmlasmo.literalura.repository.AuthorRepository;
import com.lmlasmo.literalura.repository.BookRepository;
import com.lmlasmo.literalura.repository.LanguageRepository;

@Service
public class Menu {

	private ConsumeGuntendexAPI api;
	private Scanner scanner;

	private Register register;

	String showAuthorModel = """
			Name: %s
			Ano de nascimento: %s
			Ano de falecimento: %s
			""";

	String showBookModel = """
			Título: %s
			Autor(es): %s
			Language: %s
			Número de Downloads: %s
			""";

	@Autowired
	public Menu(Register register) {

		this.api = new ConsumeGuntendexAPI();
		this.register = register;
		this.scanner = new Scanner(System.in);

	}

	public void showMenu() {

		String options = """
				----------
				Escolha o número de sua opção:
				1- Buscar livro pelo titulo
				2- Listar livros registrados
				3- Listar autores registrados
				4- Listar autores vivos em determinado ano
				5- Listar livros em um determinado idioma
				0- Sair
				""";

		System.out.println(options);

		int option = -1;

		try {

			option = Integer.parseInt(scanner.nextLine());

		} catch (InputMismatchException e) {
			System.out.println("Deve-se passar uma opção numérica");
		}

		switch (option) {

		case 0:
			System.out.println("Esperamos seu próximo uso :)");
			break;

		case 1:
			findBookOption();
			System.out.print(":");
			scanner.nextLine();
			showMenu();
			break;

		case 2:
			toListBooksOption();
			System.out.print(":");
			scanner.nextLine();
			showMenu();
			break;

		case 3:
			toListAuthorsOption();
			System.out.print(":");
			scanner.nextLine();
			showMenu();
			break;

		case 4:
			toListAuthorsLivingOption();
			System.out.print(":");
			scanner.nextLine();
			showMenu();
			break;

		case 5:
			toListBookByLanguageOption();
			System.out.print(":");
			scanner.nextLine();
			showMenu();
			break;

		default:
			System.out.println("Opção não reconhecida.Por favor tente de novo.");
			showMenu();

		}

	}

	private void findBookOption() {

		System.out.println("Por qual titulo deseja buscar?");

		String title = scanner.nextLine();

		List<BookDTO> bookDtoList = api.requestSearch(title);

		Optional<BookDTO> bookDtoOp = bookDtoList.stream()
				.filter(b -> b.getTitle().substring(0, title.length()).toUpperCase().equals(title.toUpperCase()))
				.distinct().findFirst();

		if (bookDtoOp.isPresent()) {

			Book book = register.registeBook(bookDtoOp.get());

			List<String> autoresList = book.getAuthors().stream().map(a -> a.getName()).toList();

			List<String> languageList = book.getLanguages().stream().map(l -> l.getLanguage()).toList();

			String autores = String.join("; ", autoresList);

			String languages = String.join(", ", languageList);

			System.out.printf(showBookModel, book.getTitle(), autores, languages,
					Long.toString(book.getDownloadCount()));

		} else {

			System.out.println("Não foi encontrado nenhum livro com esse nome");

		}

	}

	private void toListBooksOption() {

		List<Book> bookList = register.getAllBooks();

		bookList.stream().forEach(b -> {

			List<String> autoresList = b.getAuthors().stream().map(a -> a.getName()).toList();

			List<String> languageList = b.getLanguages().stream().map(l -> l.getLanguage()).toList();

			String autores = String.join("; ", autoresList);
			String languages = String.join(", ", languageList);

			System.out.printf(showBookModel, b.getTitle(), autores, languages, b.getDownloadCount(), "\n");

		});

	}

	private void toListAuthorsOption() {

		List<Author> authors = register.getAllAuthors();

		authors.stream().forEach(a -> {

			String birth = "Não consta";
			String death = "Não consta";

			if (a.getBirthYear() != null) {
				birth = a.getBirthYear().toString();
			}

			if (a.getDeathYear() != null) {
				death = a.getDeathYear().toString();
			}

			System.out.printf(showAuthorModel, a.getName(), birth, death, "\n");

		});

	}

	private void toListAuthorsLivingOption() {

		System.out.println("Em qual ano deseja realizar a busca?");

		try {

			int ano = scanner.nextInt();

			int anoAtual = LocalDate.now().getYear();

			if (ano <= anoAtual) {

				List<Author> authors = register.getAuthorRepository().findLivingAuthors(ano);

				if (authors.size() > 0) {

					authors.stream().forEach(a -> {

						String birth = "Não consta";
						String death = "Não consta";

						if (a.getBirthYear() != null) {
							birth = a.getBirthYear().toString();
						}

						if (a.getDeathYear() != null) {
							death = a.getDeathYear().toString();
						}

						System.out.printf(showAuthorModel, a.getName(), birth, death, "\n");

					});

				} else {
					System.out.println("Não foi encontrado autores vivos nesse ano");
				}

			} else {
				System.out.println(ano + " está no futuro. Tente outro ano");
			}

		} catch (InputMismatchException e) {
			System.out.println("Parece que você não passou um valor numérico. Por favor tente de novo");
		}

	}

	private void toListBookByLanguageOption() {

		String languageOptions = """
				Use o número do idioma corresponte:
				1- pt
				2- es
				3- en
				4- fr
				""";

		System.out.println(languageOptions);

		try {

			int lan = scanner.nextInt();

			switch (lan) {

			case 1:
				toListBookByLanguage("pt");
				break;
			case 2:
				toListBookByLanguage("es");
				break;
			case 3:
				toListBookByLanguage("en");
				break;
			case 4:
				toListBookByLanguage("fr");
				break;
			default:
				System.out.println("Não há um idioma correspondente");

			}

		} catch (InputMismatchException e) {

			System.out.println("Deve-se passar uma opção numérica");

		}

	}

	private void toListBookByLanguage(String lan) {

		List<Book> bookList = register.getBookRepository().findByLanguagesLanguage(lan);

		bookList.stream().forEach(b -> {

			List<String> autoresList = b.getAuthors().stream().map(a -> a.getName()).toList();

			List<String> languageList = b.getLanguages().stream().map(l -> l.getLanguage()).toList();

			String autores = String.join("; ", autoresList);
			String languages = String.join(", ", languageList);

			System.out.printf(showBookModel, b.getTitle(), autores, languages, b.getDownloadCount(), "\n");

		});

	}

}