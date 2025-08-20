package com.literalura.literalura.repository;

import com.literalura.literalura.model.Book; // Importa la clase Book
import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository
import org.springframework.stereotype.Repository; // Anotación @Repository
import java.util.List;
import java.util.Optional; // Para manejar la posibilidad de no encontrar un libro

/**
 * Interfaz de repositorio para la entidad Book.
 * Extiende JpaRepository para obtener métodos CRUD (Crear, Leer, Actualizar, Borrar)
 * y paginación sin escribir código.
 * El primer parámetro es la entidad (Book) y el segundo es el tipo de su ID (Integer).
 */
@Repository // Indica a Spring que esta interfaz es un componente de repositorio.
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Busca un libro por su título, ignorando mayúsculas y minúsculas.
     * Útil para verificar si un libro ya existe en la base de datos
     * y para la búsqueda principal.
     * Spring Data JPA automáticamente crea la consulta a partir del nombre del método.
     * @param title El título del libro a buscar.
     * @return Un Optional que contiene el libro si se encuentra, o un Optional vacío.
     */
    Optional<Book> findByTitleIgnoreCase(String title);

    /**
     * Busca libros que contengan una determinada cadena de texto en su título,
     * ignorando mayúsculas y minúsculas.
     * @param title El fragmento del título a buscar.
     * @return Una lista de libros que coinciden con el título.
     */
    List<Book> findByTitleContainingIgnoreCase(String title);

    /**
     * Busca todos los libros registrados en la base de datos en un idioma específico.
     * @param language El código de idioma (ej. "es", "en", "fr", "pt").
     * @return Una lista de libros que están en el idioma especificado.
     */
    List<Book> findByLanguagesContaining(String language);

    /**
     * Verifica si existe un libro con un título específico, ignorando mayúsculas y minúsculas.
     * Este método puede ser útil para la validación de duplicados de forma más eficiente.
     * @param title El título del libro a verificar.
     * @return true si existe un libro con ese título, false en caso contrario.
     */
    boolean existsByTitleIgnoreCase(String title);
}
