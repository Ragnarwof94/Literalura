package com.literalura.literalura.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*; // Nuevas importaciones para JPA
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Necesario para toString

/**
 * Clase de modelo para representar un libro individual,
 * ahora también como una entidad JPA para la persistencia en base de datos.
 */
@Entity // Anotación clave: marca esta clase como una entidad JPA, mapeada a una tabla en la DB.
@Table(name = "books") // Define el nombre de la tabla en la base de datos.
public class Book {
    @Id // Marca este campo como la clave primaria de la tabla.
    private Integer id; // Usamos Integer ya que el ID de Gutendex es un Integer y lo mantendremos.

    @Column(unique = true, nullable = false) // 'title' es una columna en la DB, debe ser única y no nula.
    private String title;

    // Relación Many-to-One: Muchos libros pueden tener un mismo autor.
    // @ManyToOne establece la relación.
    // @JoinColumn indica la columna en la tabla 'books' que almacena el ID del autor.
    @ManyToOne(cascade = CascadeType.PERSIST) // CascadeType.PERSIST: Si guardo un libro, guarda su autor si es nuevo.
    @JoinColumn(name = "author_id", nullable = false) // Columna para la FK, no nula.
    private Author author; // Un libro tiene un único autor (simplificamos para este proyecto).

    // @ElementCollection y @CollectionTable para almacenar listas de Strings como una tabla separada.
    // 'fetch = FetchType.EAGER' carga los idiomas inmediatamente.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private List<String> languages; // Lista de idiomas del libro.

    // @Transient: Ignora este campo para la persistencia en la base de datos.
    // Solo lo queremos para el JSON de la API, no para guardar.
    @Transient
    private List<String> subjects;

    @SerializedName("download_count")
    @Column(name = "download_count") // Mapea a una columna con este nombre.
    private Integer downloadCount;

    // Constructor vacío: Necesario para JPA y Gson.
    public Book() {
    }

    // Constructor para inicializar un Book desde la respuesta de la API.
    // Toma una lista de autores de la API, pero en la DB solo guardaremos el primero.
    public Book(Integer id, String title, List<Author> authorsFromApi, List<String> subjects, List<String> languages, Integer downloadCount) {
        this.id = id;
        this.title = title;
        // Asignamos el primer autor de la lista para simplificar la relación ManyToOne.
        // Si la lista está vacía, el autor será null, lo cual puede requerir manejo de errores.
        this.author = authorsFromApi != null && !authorsFromApi.isEmpty() ? authorsFromApi.get(0) : null;
        this.subjects = subjects; // Este campo es transitorio, solo para JSON.
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    // Métodos Getter
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    // Métodos Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    // Método toString para una representación de texto del objeto Book
    @Override
    public String toString() {
        String authorName = (author != null && author.getName() != null) ? author.getName() : "Desconocido";

        return "--- LIBRO ---\n" +
                "Título: " + title + "\n" +
                "Autor(es): " + authorName + "\n" +
                "Idioma(s): " + (languages != null && !languages.isEmpty() ? String.join(", ", languages) : "Desconocido") + "\n" +
                "Número de descargas: " + (downloadCount != null ? downloadCount : "N/A") + "\n" +
                "-------------\n";
    }

    public Map<Object, Object> getAuthors() {
        return Map.of();
    }
}
