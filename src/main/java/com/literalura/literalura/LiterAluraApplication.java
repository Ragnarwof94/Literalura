package com.literalura.literalura;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.literalura.literalura.model.Author;
import com.literalura.literalura.model.Book;
import com.literalura.literalura.model.GutendexResponse;
import com.literalura.literalura.repository.AuthorRepository; // Nueva importación
import com.literalura.literalura.repository.BookRepository;   // Nueva importación
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException; // Para manejar duplicados

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal de la aplicación LiterAlura.
 * Esta clase inicializa el contexto de Spring Boot, maneja la interacción por consola
 * y coordina las operaciones con la API Gutendex y la base de datos.
 */
@SpringBootApplication
public class LiterAluraApplication {

    private static final String API_URL = "https://gutendex.com/books/";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Inyección de dependencias: Spring automáticamente provee instancias de los repositorios.
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Scanner scanner = new Scanner(System.in); // Scanner para toda la aplicación

    // Constructor para inyectar los repositorios.
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
     * Busca un libro por título en la API Gutendex y lo guarda en la base de datos.
     * Maneja la verificación de duplicados y errores de API/conexión.
     */
    private void searchAndSaveBook() {
        System.out.print("Ingresa el título del libro a buscar: ");
        String searchTitle = scanner.nextLine();

        // 1. Verificar si el libro ya existe en la base de datos
        if (bookRepository.existsByTitleIgnoreCase(searchTitle)) {
            System.out.println("\n--- ERROR ---");
            System.out.println("¡El libro '" + searchTitle + "' ya está registrado en la base de datos!");
            System.out.println("-------------\n");
            return; // Salir del método
        }

        String encodedTitle = searchTitle.replace(" ", "%20");
        URI uri = URI.create(API_URL + "?search=" + encodedTitle);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

        System.out.println("\nRealizando búsqueda de: '" + searchTitle + "' en Gutendex y registrando...");

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                GutendexResponse gutendexData = gson.fromJson(jsonResponse, GutendexResponse.class);

                List<Book> booksFound = gutendexData.getBooks();

                if (booksFound != null && !booksFound.isEmpty()) {
                    // Tomamos el primer libro encontrado por simplicidad.
                    Book bookToSave = booksFound.get(0);

                    // --- INICIO DE CAMBIOS PARA MANEJO DE AUTORES NULOS ---
                    Author apiAuthorFromList = null;
                    String authorNameForDb = "Desconocido"; // Nombre por defecto si no hay autor válido

                    // Verificamos si la lista de autores no es nula ni vacía, Y si el primer autor no es nulo
                    if (bookToSave.getAuthors() != null && !bookToSave.getAuthors().isEmpty()) {
                        if (bookToSave.getAuthors().get(0) != null) {
                            apiAuthorFromList = (Author) bookToSave.getAuthors().get(0);
                            // Asegurarse de que el nombre del autor de la API no sea nulo ni vacío
                            if (apiAuthorFromList.getName() != null && !apiAuthorFromList.getName().trim().isEmpty()) {
                                authorNameForDb = apiAuthorFromList.getName();
                            } else {
                                System.out.println("Advertencia: Autor de '" + bookToSave.getTitle() + "' tiene un nombre vacío/nulo. Usando 'Desconocido'.");
                            }
                        } else {
                            System.out.println("Advertencia: El primer autor de '" + bookToSave.getTitle() + "' es nulo en la respuesta de la API. Usando 'Desconocido'.");
                        }
                    } else {
                        System.out.println("Advertencia: Libro '" + bookToSave.getTitle() + "' no tiene autores en la respuesta de la API. Usando 'Desconocido'.");
                    }

                    // Buscar el autor en la DB, si no existe, crear uno nuevo
                    Optional<Author> existingAuthor = authorRepository.findByNameIgnoreCase(authorNameForDb);
                    Author author;
                    if (existingAuthor.isPresent()) {
                        author = existingAuthor.get();
                        System.out.println("Autor '" + authorNameForDb + "' ya existente en la base de datos.");
                    } else {
                        // Si el autor no existe, creamos uno nuevo con la info de la API
                        // Si apiAuthorFromList es null o su nombre es "Desconocido", creamos un autor "Desconocido"
                        if (apiAuthorFromList != null && !authorNameForDb.equals("Desconocido")) {
                            author = new Author(apiAuthorFromList.getName(), apiAuthorFromList.getBirthYear(), apiAuthorFromList.getDeathYear());
                        } else {
                            // Crear un autor "Desconocido" con años nulos si no hay info válida del autor
                            author = new Author("Desconocido", null, null);
                        }
                        try {
                            authorRepository.save(author); // Guardar el nuevo autor
                            System.out.println("Nuevo autor '" + author.getName() + "' registrado.");
                        } catch (DataIntegrityViolationException e) {
                            // Esto puede ocurrir si, por ejemplo, múltiples hilos intentan insertar "Desconocido" al mismo tiempo.
                            // Intentamos recuperarlo si ya existe.
                            System.err.println("Advertencia: El autor '" + author.getName() + "' ya fue insertado concurrently. Recuperando existente.");
                            author = authorRepository.findByNameIgnoreCase(author.getName())
                                    .orElseThrow(() -> new RuntimeException("Error al recuperar autor existente después de un conflicto."));
                        }
                    }
                    // --- FIN DE CAMBIOS PARA MANEJO DE AUTORES NULOS ---

                    // Asociar el autor al libro
                    bookToSave.setAuthor(author);

                    // Guardar el libro en la base de datos
                    bookRepository.save(bookToSave);
                    System.out.println("\n--- LIBRO REGISTRADO EXITOSAMENTE ---");
                    System.out.println(bookToSave.toString());
                    System.out.println("-----------------------------------\n");

                } else {
                    System.out.println("\n--- BÚSQUEDA SIN RESULTADOS ---");
                    System.out.println("No se encontró ningún libro con el título '" + searchTitle + "' en Gutendex.");
                    System.out.println("-------------------------------\n");
                }

            } else {
                System.err.println("Error al buscar libros en la API. Código de estado: " + response.statusCode());
                System.err.println("Cuerpo de la respuesta de error: " + response.body());
            }
        } catch (DataIntegrityViolationException e) {
            // Esto maneja específicamente el caso de intentar insertar un libro duplicado
            // por alguna razón no capturada por existsByTitleIgnoreCase (por ejemplo, concurrencia)
            System.err.println("\n--- ERROR DE BASE DE DATOS ---");
            System.err.println("Error al guardar: El libro '" + searchTitle + "' ya existe en la base de datos.");
            System.out.println("-----------------------------\n");
        } catch (IOException e) {
            System.err.println("Error de I/O al comunicarse con la API: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("La operación de red fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (JsonSyntaxException e) {
            System.err.println("Error al parsear la respuesta JSON de la API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado durante la búsqueda/guardado: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace completo para depuración
        }
    }

    /**
     * Lista todos los libros que están registrados en la base de datos.
     */
    private void listRegisteredBooks() {
        List<Book> books = bookRepository.findAll(); // Usa el método findAll del repositorio.
        if (books.isEmpty()) {
            System.out.println("\nNo hay libros registrados en la base de datos.");
        } else {
            System.out.println("\n--- LIBROS REGISTRADOS ---");
            books.forEach(System.out::println); // Imprime cada libro usando su toString().
            System.out.println("------------------------\n");
        }
    }

    /**
     * Lista todos los autores registrados en la base de datos.
     */
    private void listRegisteredAuthors() {
        List<Author> authors = authorRepository.findAllByOrderByNameAsc(); // Usa el método personalizado.
        if (authors.isEmpty()) {
            System.out.println("\nNo hay autores registrados en la base de datos.");
        } else {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            authors.forEach(author -> System.out.println(author.getName())); // Solo imprime el nombre del autor
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
            scanner.nextLine(); // Consumir el salto de línea

            List<Author> authors = authorRepository.findAuthorsAliveInYear(year); // Usa el método personalizado.
            if (authors.isEmpty()) {
                System.out.println("\nNo se encontraron autores vivos en el año " + year + ".");
            } else {
                System.out.println("\n--- AUTORES VIVOS EN " + year + " ---");
                authors.forEach(System.out::println); // Imprime cada autor usando su toString().
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
        System.out.println("\nIdiomas disponibles (ej. ES, EN, FR, PT): ");
        System.out.print("Ingresa el código de idioma: ");
        String language = scanner.nextLine().toLowerCase(); // Leer y convertir a minúsculas para coincidir con la API

        // Opcional: Validar que el idioma sea uno de los permitidos
        if (!List.of("es", "en", "fr", "pt").contains(language)) {
            System.out.println("Idioma no válido. Por favor, ingresa ES, EN, FR o PT.");
            return;
        }

        List<Book> books = bookRepository.findByLanguagesContaining(language); // Usa el método personalizado.
        if (books.isEmpty()) {
            System.out.println("\nNo se encontraron libros en " + language.toUpperCase() + " en la base de datos.");
        } else {
            System.out.println("\n--- LIBROS EN IDIOMA " + language.toUpperCase() + " ---");
            books.forEach(System.out::println); // Imprime cada libro.
            System.out.println("------------------------------------\n");
        }
    }
}
