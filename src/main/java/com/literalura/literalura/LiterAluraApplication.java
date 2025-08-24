package com.literalura.literalura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.GutendexResponse;
import com.literalura.literalura.repository.AuthorRepository;
import com.literalura.literalura.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Clase principal de la aplicación LiterAlura.
 * Esta clase inicializa el contexto de Spring Boot, maneja la interacción por consola
 * y coordina las operaciones con la API Gutendex y la base de datos.
 */
@SpringBootApplication
public class LiterAluraApplication {

    private static final String API_URL = "https://gutendex.com/books/";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Scanner scanner = new Scanner(System.in);

    public LiterAluraApplication(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Bean
    public CommandLineRunner runApplication() {
        return args -> {
            boolean running = true;

            System.out.println("¡Bienvenido a LiterAlura, tu catálogo de libros!");

            while (running) {
                System.out.println("\n--- MENÚ ---");
                System.out.println("1. Buscar libro por título");
                System.out.println("2. Listar libros registrados");
                System.out.println("3. Listar autores registrados");
                System.out.println("4. Listar autores vivos en un determinado año");
                System.out.println("5. Listar libros por idioma");
                System.out.println("0. Salir");
                System.out.print("Elige una opción: ");

                try {
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea

                    switch (option) {
                        case 1:
                            searchAndSaveBook();
                            break;
                        case 2:
                            listRegisteredBooks();
                            break;
                        case 3:
                            listRegisteredAuthors();
                            break;
                        case 4:
                            listAuthorsAliveInYear();
                            break;
                        case 5:
                            listBooksByLanguage();
                            break;
                        case 0:
                            running = false;
                            System.out.println("Saliendo de LiterAlura. ¡Hasta pronto!");
                            break;
                        default:
                            System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Entrada inválida. Por favor, ingresa un número del menú.");
                    scanner.nextLine(); // Limpiar el buffer del scanner
                } catch (Exception e) {
                    System.err.println("Ocurrió un error inesperado en la aplicación: " + e.getMessage());
                }
            }
            scanner.close();
        };
    }

    /**
     * Busca un libro por título en la API Gutendex, muestra una lista de resultados
     * y permite al usuario seleccionar cuál desea guardar en la base de datos.
     * Incluye la opción de filtrar por idioma en la búsqueda a la API.
     * Maneja la verificación de duplicados y errores de API/conexión.
     */
    private void searchAndSaveBook() {
        System.out.print("Ingresa el título del libro a buscar: ");
        String searchTitle = scanner.nextLine();

        // Preguntar por un filtro de idioma
        System.out.print("¿Deseas filtrar por idioma? (ej. es, en, fr, pt, de - dejar en blanco para todos): ");
        String languageFilter = scanner.nextLine().trim().toLowerCase(); // Leer y limpiar

        String encodedTitle = searchTitle.replace(" ", "%20");
        StringBuilder uriBuilder = new StringBuilder(API_URL + "?search=" + encodedTitle);

        // Añadir filtro de idioma si se proporcionó y es válido
        if (!languageFilter.isEmpty()) {
            if (List.of("es", "en", "fr", "pt", "de").contains(languageFilter)) {
                uriBuilder.append("&languages=").append(languageFilter);
            } else {
                System.out.println("Advertencia: Idioma '" + languageFilter + "' no reconocido. Buscando sin filtro de idioma.");
            }
        }

        URI uri = URI.create(uriBuilder.toString());
        // System.out.println("DEBUG: URI de búsqueda: " + uri); // Depuración eliminada

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        System.out.println("\nRealizando búsqueda de: '" + searchTitle + "' en Gutendex...");

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                GutendexResponse gutendexData = gson.fromJson(jsonResponse, GutendexResponse.class);

                List<Book> booksFound = gutendexData.getBooks();

                if (booksFound != null && !booksFound.isEmpty()) {
                    System.out.println("\n--- RESULTADOS ENCONTRADOS ---");
                    for (int i = 0; i < Math.min(booksFound.size(), 10); i++) {
                        Book currentBook = booksFound.get(i);
                        String authorName = "Desconocido";
                        // Utiliza el getter del autor ya asignado por Book.java (PostLoad/setter)
                        if (currentBook.getAuthor() != null && currentBook.getAuthor().getName() != null) {
                            authorName = currentBook.getAuthor().getName();
                        }
                        System.out.println((i + 1) + ". Título: " + currentBook.getTitle() + " | Autor: " + authorName + " | Idioma: " + (currentBook.getLanguages() != null && !currentBook.getLanguages().isEmpty() ? currentBook.getLanguages().get(0) : "N/A"));
                    }
                    System.out.print("Ingresa el número del libro que deseas registrar (o 0 para cancelar): ");

                    int selection = -1;
                    try {
                        selection = scanner.nextInt();
                        scanner.nextLine(); // Consumir salto de línea
                    } catch (InputMismatchException e) {
                        System.err.println("Entrada inválida. Por favor, ingresa un número.");
                        scanner.nextLine(); // Limpiar buffer
                        return;
                    }

                    if (selection > 0 && selection <= Math.min(booksFound.size(), 10)) {
                        Book bookToSave = booksFound.get(selection - 1); // Elige el libro seleccionado por el usuario

                        // Verificar si el libro ya existe en la base de datos ANTES de intentar guardarlo
                        if (bookRepository.existsByTitleIgnoreCase(bookToSave.getTitle())) {
                            System.out.println("\n--- ERROR ---");
                            System.out.println("¡El libro '" + bookToSave.getTitle() + "' ya está registrado en la base de datos!");
                            System.out.println("-------------\n");
                            return; // Salir del método
                        }

                        // --- INICIO DE MANEJO DE AUTORES ---
                        // Extraemos los datos del autor principal directamente de la lista apiAuthors del libro seleccionado.
                        // Esto asegura que usamos los datos tal cual llegaron de la API para ese libro.
                        String authorNameForDb = "Desconocido";
                        Integer birthYearForDb = null;
                        Integer deathYearForDb = null;

                        if (bookToSave.getApiAuthors() != null && !bookToSave.getApiAuthors().isEmpty() && bookToSave.getApiAuthors().get(0) != null) {
                            Author primaryApiAuthor = bookToSave.getApiAuthors().get(0);
                            if (primaryApiAuthor.getName() != null && !primaryApiAuthor.getName().trim().isEmpty()) {
                                authorNameForDb = primaryApiAuthor.getName();
                                birthYearForDb = primaryApiAuthor.getBirthYear();
                                deathYearForDb = primaryApiAuthor.getDeathYear();
                            } else {
                                System.out.println("Advertencia: Autor de '" + bookToSave.getTitle() + "' tiene un nombre vacío/nulo en la API. Usando 'Desconocido'.");
                            }
                        } else {
                            System.out.println("Advertencia: Libro '" + bookToSave.getTitle() + "' no tiene autores válidos en la respuesta de la API. Usando 'Desconocido'.");
                        }

                        System.out.println("DEBUG: Nombre del autor para la DB: " + authorNameForDb); // Debugging extra

                        // Buscar el autor en la DB por su nombre.
                        Optional<Author> existingAuthor = authorRepository.findByNameIgnoreCase(authorNameForDb);
                        Author author;
                        if (existingAuthor.isPresent()) {
                            author = existingAuthor.get(); // Usar el autor ya existente
                            System.out.println("Autor '" + authorNameForDb + "' ya existente en la base de datos.");
                        } else {
                            // Crear un nuevo autor con los detalles obtenidos de la API (o 'Desconocido' si no hay datos válidos)
                            author = new Author(authorNameForDb, birthYearForDb, deathYearForDb);
                            try {
                                authorRepository.save(author); // Guardar el nuevo autor
                                System.out.println("Nuevo autor '" + author.getName() + "' registrado.");
                            } catch (DataIntegrityViolationException e) {
                                System.err.println("Advertencia: El autor '" + author.getName() + "' ya fue insertado concurrently. Recuperando existente.");
                                author = authorRepository.findByNameIgnoreCase(author.getName())
                                        .orElseThrow(() -> new RuntimeException("Error al recuperar autor existente después de un conflicto."));
                            }
                        }
                        // --- FIN DE MANEJO DE AUTORES ---

                        bookToSave.setAuthor(author); // Asociar el autor de la DB (existente o nuevo) al libro
                        bookRepository.save(bookToSave); // Guardar el libro
                        System.out.println("\n--- LIBRO REGISTRADO EXITOSAMENTE ---");
                        System.out.println(bookToSave.toString()); // El toString() del libro debe ahora mostrar el autor correcto
                        System.out.println("-----------------------------------\n");

                    } else if (selection == 0) {
                        System.out.println("Búsqueda cancelada por el usuario.");
                    } else {
                        System.out.println("Selección inválida. Por favor, elige un número de la lista.");
                    }

                } else { // No hay libros encontrados
                    System.out.println("\n--- BÚSQUEDA SIN RESULTADOS ---");
                    System.out.println("No se encontró ningún libro con el título '" + searchTitle + "' en Gutendex.");
                    System.out.println("-------------------------------\n");
                }

            } else { // Error de status code
                System.err.println("Error al buscar libros en la API. Código de estado: " + response.statusCode());
                System.err.println("Cuerpo de la respuesta de error: " + response.body());
            }
        } catch (DataIntegrityViolationException e) {
            System.err.println("\n--- ERROR DE BASE DE DATOS ---");
            System.err.println("Error al guardar: El libro '" + searchTitle + "' ya existe en la base de datos. Detalles: " + e.getMessage());
            System.out.println("-----------------------------\n");
        } catch (IOException e) {
            System.err.println("Error de I/O al comunicarse con la API: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La operación de red fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (JsonSyntaxException e) {
            System.err.println("Error al parsear la respuesta JSON de la API. Posiblemente el formato no es el esperado: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado durante la búsqueda/guardado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lista todos los libros que están registrados en la base de datos.
     */
    private void listRegisteredBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            System.out.println("\nNo hay libros registrados en la base de datos.");
        } else {
            System.out.println("\n--- LIBROS REGISTRADOS ---");
            books.forEach(System.out::println);
            System.out.println("------------------------\n");
        }
    }

    /**
     * Lista todos los autores registrados en la base de datos, excluyendo los autores "Desconocido".
     */
    private void listRegisteredAuthors() {
        List<Author> authors = authorRepository.findAllByOrderByNameAsc().stream()
                .filter(author -> !author.getName().equalsIgnoreCase("Desconocido"))
                .collect(Collectors.toList());

        if (authors.isEmpty()) {
            System.out.println("\nNo hay autores registrados (o todos son 'Desconocido').");
        } else {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            authors.forEach(author -> System.out.println(author.toString())); // Usar toString() del Author
            System.out.println("---------------------------\n");
        }
    }

    /**
     * Lista los autores que estaban vivos en un año específico.
     */
    private void listAuthorsAliveInYear() {
        System.out.print("Ingresa el año para buscar autores vivos: ");
        try {
            int year = scanner.nextInt();
            scanner.nextLine();

            List<Author> authors = authorRepository.findAuthorsAliveInYear(year);
            authors = authors.stream()
                    .filter(author -> !author.getName().equalsIgnoreCase("Desconocido"))
                    .collect(Collectors.toList());

            if (authors.isEmpty()) {
                System.out.println("\nNo se encontraron autores vivos en el año " + year + " (excluyendo 'Desconocido').");
            } else {
                System.out.println("\n--- AUTORES VIVOS EN " + year + " ---");
                authors.forEach(System.out::println);
                System.out.println("------------------------------\n");
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Por favor, ingresa un número para el año.");
            scanner.nextLine();
        }
    }

    /**
     * Lista los libros registrados en la base de datos por un idioma específico.
     */
    private void listBooksByLanguage() {
        System.out.println("\nIdiomas disponibles (ej. ES, EN, FR, PT, DE): ");
        System.out.print("Ingresa el código de idioma: ");
        String language = scanner.nextLine().toLowerCase();

        if (!List.of("es", "en", "fr", "pt", "de").contains(language)) {
            System.out.println("Idioma no válido. Por favor, ingresa ES, EN, FR, PT o DE.");
            return;
        }

        List<Book> books = bookRepository.findByLanguagesContaining(language);
        if (books.isEmpty()) {
            System.out.println("\nNo se encontraron libros en " + language.toUpperCase() + " en la base de datos.");
        } else {
            System.out.println("\n--- LIBROS EN IDIOMA " + language.toUpperCase() + " ---");
            books.forEach(System.out::println);
            System.out.println("------------------------------------\n");
        }
    }
}
