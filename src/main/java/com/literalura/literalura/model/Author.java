package com.literalura.literalura.model;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*; // Importaciones para JPA
import java.util.List;
import java.util.Objects; // Para equals y hashCode

/**
 * Clase de modelo para representar un autor de un libro,
 * ahora también como una entidad JPA para la persistencia en base de datos.
 */
@Entity // Anotación clave: marca esta clase como una entidad JPA, mapeada a una tabla en la DB.
@Table(name = "authors") // Define el nombre de la tabla en la base de datos.
public class Author {
    @Id // Marca este campo como la clave primaria de la tabla.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el ID para nuevos autores.
    private Long id; // Usamos Long para IDs de base de datos.

    @Column(unique = true, nullable = false) // 'name' es una columna en la DB, debe ser única y no nula.
    private String name;

    @SerializedName("birth_year")
    private Integer birthYear;

    @SerializedName("death_year")
    private Integer deathYear;

    // Relación One-to-Many: Un autor puede tener muchos libros.
    // 'mappedBy' indica que la relación es gestionada por el campo 'author' en la entidad Book.
    // 'FetchType.EAGER' carga los libros del autor inmediatamente (puede ser ineficiente para muchos libros).
    // 'CascadeType.ALL' propaga operaciones (persistencia, eliminación) a los libros asociados.
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books; // Lista de libros asociados a este autor.

    // Constructor vacío: Necesario para JPA y Gson.
    public Author() {
    }

    // Constructor para inicializar un Author con datos.
    // No incluye 'id' ya que se genera automáticamente.
    public Author(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    // Métodos Getter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    // Métodos Setter
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // toString para una representación de texto del objeto Author
    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("  - Nombre: " + name);
        if (birthYear != null) {
            info.append(", Nacimiento: ").append(birthYear);
        }
        if (deathYear != null) {
            info.append(", Fallecimiento: ").append(deathYear);
        }
        return info.toString();
    }

    // Implementación de equals y hashCode para comparar objetos Author por su nombre.
    // Esto es crucial para evitar autores duplicados en la base de datos.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name); // Considera dos autores iguales si tienen el mismo nombre
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // El hashCode se basa en el nombre
    }
}
