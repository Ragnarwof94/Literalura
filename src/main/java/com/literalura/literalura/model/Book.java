package com.literalura.literalura.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad JPA que representa un libro.
 * Se alimenta con datos de la API Gutendex y se persiste en PostgreSQL.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    private Integer id; // ID original de Gutendex

    @Column(unique = true, nullable = false)
    private String title;

    // Relación muchos-a-uno: varios libros pueden tener un mismo autor.
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Campo transitorio: lista de autores recibida del JSON de la API.
    @Transient
    @SerializedName("authors")
    private List<Author> apiAuthors;

    // Idiomas (guardados en tabla separada book_languages).
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private List<String> languages;

    // Materias o categorías (solo de la API, no se persisten en DB).
    @Transient
    private List<String> subjects;

    @SerializedName("download_count")
    @Column(name = "download_count")
    private Integer downloadCount;

    // Constructor vacío para JPA y Gson
    public Book() {}

    // Constructor desde respuesta de API
    public Book(Integer id, String title, List<Author> authorsFromApi, List<String> subjects,
                List<String> languages, Integer downloadCount) {
        this.id = id;
        this.title = title;
        this.apiAuthors = authorsFromApi;
        this.subjects = subjects;
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    // --- Getters ---
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }
    public List<Author> getApiAuthors() { return apiAuthors; }
    public List<String> getSubjects() { return subjects; }
    public List<String> getLanguages() { return languages; }
    public Integer getDownloadCount() { return downloadCount; }

    // --- Setters ---
    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(Author author) { this.author = author; }
    public void setApiAuthors(List<Author> apiAuthors) { this.apiAuthors = apiAuthors; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }
    public void setLanguages(List<String> languages) { this.languages = languages; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    // Representación de texto
    @Override
    public String toString() {
        String authorName = (author != null && author.getName() != null)
                ? author.getName() : "Desconocido";
        String langs = (languages != null && !languages.isEmpty())
                ? String.join(", ", languages) : "N/A";

        return "\n📖 Libro\n" +
                "   ID: " + id + "\n" +
                "   Título: " + title + "\n" +
                "   Autor: " + authorName + "\n" +
                "   Idiomas: " + langs + "\n" +
                "   Descargas: " + (downloadCount != null ? downloadCount : "N/A") + "\n";
    }
}
