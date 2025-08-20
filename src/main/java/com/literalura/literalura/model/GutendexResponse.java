package com.literalura.literalura.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Clase de modelo que representa la estructura principal de la respuesta JSON
 * de la API Gutendex al buscar libros.
 * Contiene metadatos como el conteo total de resultados, enlaces para paginación
 * y la lista de libros encontrados ('results').
 */
public class GutendexResponse {
    private Integer count; // Número total de resultados.
    private String next; // URL para la siguiente página de resultados (si existe).
    private String previous; // URL para la página anterior de resultados (si existe).

    // @SerializedName("results") mapea el campo JSON 'results' a nuestra variable 'books'.
    @SerializedName("results")
    private List<com.literalura.literalura.model.Book> books; // Lista de objetos Book encontrados en la búsqueda.

    // Constructor vacío: Necesario para Gson para poder deserializar el JSON.
    // Gson utiliza este constructor sin argumentos para crear una instancia de la clase
    // antes de rellenar sus campos con los datos del JSON.
    public GutendexResponse() {
    }

    // Constructor para inicializar una instancia de GutendexResponse con sus datos.
    public GutendexResponse(Integer count, String next, String previous, List<com.literalura.literalura.model.Book> books) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.books = books;
    }

    // Métodos Getter: Permiten acceder a los valores de las propiedades de un objeto GutendexResponse.
    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public String getPrevious() {
        return previous;
    }

    public List<com.literalura.literalura.model.Book> getBooks() {
        return books;
    }

    // Métodos Setter: Permiten modificar los valores de las propiedades (opcional).
    // Son útiles si en algún momento necesitas cambiar dinámicamente la información
    // de la respuesta o reconstruir el objeto.
    public void setCount(Integer count) {
        this.count = count;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public void setBooks(List<com.literalura.literalura.model.Book> books) {
        this.books = books;
    }

    // Método toString: Proporciona una representación textual del objeto GutendexResponse.
    // Aunque no lo usaremos directamente en el flujo principal de la aplicación,
    // es muy útil para depuración, para ver la estructura completa de la respuesta en la consola.
    @Override
    public String toString() {
        return "GutendexResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", books=" + books +
                '}';
    }
}
