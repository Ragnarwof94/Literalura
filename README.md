# 📚 LiterAlura: Cliente de API Gutendex en Java

![Java](https://img.shields.io/badge/Java-17-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.0-green)
![License](https://img.shields.io/badge/License-MIT-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.2-orange)

Aplicación de consola desarrollada en **Java 17** que interactúa con la **API Gutendex** para buscar y gestionar información de libros y autores.
Permite realizar búsquedas filtradas, registrar los resultados en una base de datos local y consultarlos desde un menú interactivo en consola.

---

## 🌟 Características

<details>
<summary>Ver características</summary>

- 🔍 **Búsqueda de Libros**

  - Busca libros por título en la **API Gutendex**.
  - Opción de **filtrar por idioma** (`es`, `en`, `fr`, `pt`, `de`).
- 📖 **Consulta de Detalles**

  - Título, autor, idioma(s) y número de descargas.
- 🖥️ **Interfaz de Consola**

  - Menú interactivo y fácil de usar.
- 💾 **Persistencia Local**

  - Base de datos en **PostgreSQL** con **Spring Data JPA**.
  - Evita duplicados de libros y autores.
  - Manejo de autores **“Desconocido”** de forma controlada.
- 📋 **Gestión de Datos Locales**

  - Listar libros registrados.
  - Listar autores registrados (excluyendo “Desconocido”).
  - Consultar autores vivos en un año específico.
  - Listar libros registrados por idioma.
- ⚠️ **Manejo de Errores**

  - Entrada inválida en el menú.
  - Conexión o formato incorrecto de la API.
  - Registro concurrente de autores.

</details>

---

## 🛠️ Tecnologías Utilizadas

<details>
<summary>Ver tecnologías</summary>

- ☕ **Java 17**
- 🚀 **Spring Boot 3**
- 🗄️ **PostgreSQL**
- 📦 **Maven**
- 📚 **Spring Data JPA**
- 🔗 **Java HttpClient (11+)**
- 🔄 **Gson**
- 🌐 **API Gutendex**

</details>

---

## 🚀 Instalación y Ejecución

<details>
<summary>Ver instrucciones</summary>

### 🔧 Requisitos Previos

- JDK 17+
- Maven
- IntelliJ IDEA (o IDE de preferencia)
- PostgreSQL en ejecución
- PgAdmin 4 (u otro cliente de PostgreSQL)

### 1️⃣ Configuración de la Base de Datos

En `src/main/resources/application.properties` agrega:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
2️⃣ Clonar y configurar el proyecto
bash
Copiar
Editar
git clone https://github.com/tu_usuario/LiterAlura.git
cd LiterAlura
Abre el proyecto en tu IDE.

Deja que Maven descargue las dependencias (pom.xml).

Limpia y reconstruye el proyecto.

3️⃣ Ejecutar la aplicación
Abre LiterAluraApplication.java.

Ejecuta el método main.

Usa el menú en consola.

</details>
🗺️ Uso de la Aplicación
<details> <summary>Ver opciones del menú</summary>
🔍 Buscar libro por título (con opción de filtrar por idioma).

📖 Listar libros registrados.

✍️ Listar autores registrados.

👤 Listar autores vivos en un año específico.

🌐 Listar libros por idioma.

❌ Salir de la aplicación.

Ejemplo rápido en consola:

text
Copiar
Editar
¡Bienvenido a LiterAlura!
--- MENÚ ---
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma
0. Salir
Elige una opción: 1
Ingresa el título del libro a buscar: Don Quijote
¿Deseas filtrar por idioma? (ej. es, en, fr, pt, de - dejar en blanco para todos): es

--- RESULTADOS ENCONTRADOS ---
1. Título: Don Quijote | Autor: Miguel de Cervantes | Idioma: es
Selecciona el número del libro que deseas registrar: 1
--- LIBRO REGISTRADO EXITOSAMENTE ---
Título: Don Quijote
Autor(es): Miguel de Cervantes
Idioma(s): es
Número de descargas: 2500
-------------
</details>
🧩 Modelo de Datos
<details> <summary>Ver diagrama ER</summary>
mermaid
Copiar
Editar
erDiagram
    BOOKS {
        Integer id PK
        String title
        Integer download_count
    }
    AUTHORS {
        Integer id PK
        String name
        Integer birth_year
        Integer death_year
    }
    BOOK_LANGUAGES {
        Integer book_id FK
        String language
    }

    AUTHORS ||--o{ BOOKS : "escribe"
    BOOKS ||--o{ BOOK_LANGUAGES : "se traduce a"
</details>
💡 Posibles Mejoras
<details> <summary>Ver mejoras</summary>
📚 Manejo de múltiples autores por libro (actualmente solo se guarda el primero).

🔐 Autenticación/seguridad para acceso multiusuario.

📊 Generar reportes estadísticos de descargas o autores.

📝 Documentación automática con Swagger/OpenAPI.

✅ Tests unitarios y de integración con JUnit y Mockito.

</details>
🤝 Contribuciones
<details> <summary>Ver contribución</summary>
¡Las contribuciones son bienvenidas!
Abre un issue o un pull request en el repositorio.

</details>
📄 Licencia
<details> <summary>Ver licencia</summary>
Este proyecto está bajo la Licencia MIT.
Consulta el archivo LICENSE para más detalles.

</details> ```
```
