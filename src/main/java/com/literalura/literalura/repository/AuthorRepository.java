package com.literalura.literalura.repository;

import com.literalura.literalura.model.Author; // Importa la clase Author
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository
import org.springframework.data.jpa.repository.Query; // Para consultas JPQL personalizadas
import org.springframework.stereotype.Repository; // Anotación @Repository
import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio para la entidad Author.
 * Extiende JpaRepository para obtener métodos CRUD y paginación.
 * El primer parámetro es la entidad (Author) y el segundo es el tipo de su ID (Long).
 */
@Repository // Indica a Spring que esta interfaz es un componente de repositorio.
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Busca un autor por su nombre, ignorando mayúsculas y minúsculas.
     * @param name El nombre del autor a buscar.
     * @return Un Optional que contiene el autor si se encuentra, o un Optional vacío.
     */
    Optional<Author> findByNameIgnoreCase(String name);

    /**
     * Busca autores que estaban vivos en un determinado año.
     * Utiliza una consulta JPQL personalizada para filtrar autores
     * cuyo año de nacimiento es menor o igual al año dado
     * y cuyo año de fallecimiento es mayor o igual al año dado,
     * o si el año de fallecimiento es nulo (se asume que aún vive).
     * @param year El año de referencia.
     * @return Una lista de autores vivos en el año especificado, ordenados por nombre.
     */
    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear >= :year OR a.deathYear IS NULL) ORDER BY a.name")
    List<Author> findAuthorsAliveInYear(Integer year);

    /**
     * Lista todos los autores registrados, ordenados alfabéticamente por nombre.
     * @return Una lista de todos los autores.
     */
    List<Author> findAllByOrderByNameAsc();

}
