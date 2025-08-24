# 📚 LiterAlura: Cliente de API Gutendex en Java

**Este proyecto implementa una ****aplicación de consola en Java 17** diseñada para interactuar con la  **API de Gutendex** **. Permite a los usuarios buscar, registrar y gestionar información de libros y autores, almacenándola persistentemente en una base de datos local PostgreSQL.**

## 🌟 Características Destacadas

* **🔍 Búsqueda Interactiva:** Permite buscar libros por título en la API de Gutendex, ofreciendo múltiples resultados para que el usuario seleccione el libro deseado a registrar.
* **🌐 Filtrado por Idioma:** Incluye una opción para refinar las búsquedas de la API por un código de idioma específico (ej. "es" para español, "en" para inglés, "de" para alemán).
* **💾 Persistencia Robusta:** Almacena libros y autores en una base de datos PostgreSQL utilizando Spring Data JPA, asegurando la integridad de los datos y evitando duplicados.
* **✍️ Manejo Inteligente de Autores:** Extrae y persiste la información detallada del autor (nombre, año de nacimiento, año de fallecimiento) de la API, gestionando correctamente los casos de datos inconsistentes o nulos.
* **📋 Consultas Avanzadas:** Proporciona funcionalidades para listar todos los libros y autores registrados, buscar autores que estuvieron vivos en un rango de años específico, y filtrar libros por idioma.
* **💻 Interfaz de Consola Amigable:** Toda la interacción con la aplicación se realiza a través de un menú sencillo e intuitivo en la línea de comandos.
* **❌ Gestión de Errores:** Incluye manejo de excepciones para abordar problemas de comunicación con la API externa, errores de formato JSON y conflictos al guardar datos en la base de datos.

## 🛠️ Tecnologías Utilizadas

* **☕ ****Java 17+**
* **🚀 ****Spring Boot** (con `<span class="selected">spring-boot-starter-data-jpa</span>` y `<span class="selected">spring-boot-starter-validation</span>`)
* **🗄️ ****PostgreSQL**
* **📦 ****Maven**
* **📝 ****Gson** (para deserialización JSON de la API)
* **🌐 ****Java 11+ HttpClient** (para peticiones a la API)
* **📚 ****API Gutendex** (como fuente de datos de libros)

## 🚀 Guía de Inicio Rápido

**Sigue estos pasos para poner en marcha el proyecto LiterAlura en tu entorno local.**

### 📌 Requisitos Previos

**Asegúrate de tener instaladas las siguientes herramientas:**

* **Java Development Kit (JDK) 17** o una versión posterior ☕.
* **Apache Maven** 📦.
* **Un ****Entorno de Desarrollo Integrado (IDE) como IntelliJ IDEA** 🖥️.
* **Un servidor ****PostgreSQL** en ejecución localmente (puerto 5432 por defecto) 🗄️.
* **PgAdmin 4** (u otro cliente de base de datos para gestionar PostgreSQL, opcional).

### 1️⃣ Configuración de la Base de Datos

1. **Crea la base de datos:**

   * **Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.**
   * **Crea una nueva base de datos con el nombre exacto ** **`<span class="selected">literalura_db</span>`** **. Asigna **`<span class="selected">postgres</span>` como propietario.
2. **Configura las credenciales:**

   * **En tu proyecto, abre el archivo **`<span class="selected">src/main/resources/application.properties</span>`.
   * **Actualiza las siguientes líneas con la información de tu base de datos:**

   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_DB_PASSWORD_HERE
   spring.datasource.driver-class-name=org.postgresql.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

   ```

   * **¡Importante!** Reemplaza `<span class="selected">YOUR_DB_PASSWORD_HERE</span>` con la contraseña de tu usuario `<span class="selected">postgres</span>` en PostgreSQL.

### 2️⃣ Preparación del Proyecto en tu IDE

1. **Clona el repositorio:** Abre tu terminal (Git Bash, PowerShell, etc.) y ejecuta:

   ```
   git clone https://github.com/tu_usuario/LiterAlura.git
   cd LiterAlura

   ```

   *(Asegúrate de reemplazar `<span class="selected">tu_usuario</span>` con tu nombre de usuario real de GitHub).*
2. **Abre el proyecto en tu IDE:**

   * **Inicia IntelliJ IDEA y selecciona "Open" o "Abrir".**
   * **Navega a la carpeta **`<span class="selected">LiterAlura</span>` que clonaste y ábrela.
   * **El IDE debería reconocer el proyecto Maven y descargar automáticamente todas las dependencias.**
3. **Recarga y Reconstruye:**

   * **En tu IDE, ve a **`<span class="selected">Build</span>` -> `<span class="selected">Clean Project</span>`.
   * **Luego, **`<span class="selected">Build</span>` -> `<span class="selected">Rebuild Project</span>`. Esto asegura que todos los componentes se compilen correctamente.

### 3️⃣ Ejecución de la Aplicación

1. **Localiza la clase principal:** Ve a `<span class="selected">src/main/java/com/literalura/literalura/LiterAluraApplication.java</span>`.
2. **Ejecuta el método `<span class="selected">main</span>`:** Haz clic derecho dentro del código de esta clase y selecciona  **"Run 'LiterAluraApplication.main()'"** **.**
3. **La aplicación se iniciará en la consola de tu IDE, presentando un menú interactivo.**

## 🗺️ Uso de la Aplicación (Desde la Consola)

**Una vez que la aplicación esté en ejecución, interactúa con ella a través del menú de la consola, ingresando el número de la opción deseada:**

1. **Buscar libro por título:** Permite buscar libros en la API de Gutendex. Tras introducir un título, tendrás la opción de filtrar por idioma y luego seleccionar un libro de la lista de resultados para guardarlo en tu base de datos local.
2. **Listar libros registrados:** Muestra una lista detallada de todos los libros que has guardado previamente en tu base de datos local.
3. **Listar autores registrados:** Presenta una lista de todos los autores únicos almacenados en tu base de datos, excluyendo cualquier entrada de "Desconocido".
4. **Listar autores vivos en un determinado año:** Ingresa un año y la aplicación te mostrará los autores registrados que, según sus años de nacimiento y fallecimiento, estaban vivos durante ese periodo.
5. **Listar libros por idioma:** Introduce un código de idioma (ej. "es", "en", "de") y la aplicación filtrará y mostrará los libros registrados en tu base de datos que coincidan con ese idioma.
6. **Salir:** Termina la ejecución de la aplicación.

## 💡 Posibles Mejoras Futuras

* **Soporte Multi-Autor:** Implementar un modelo de datos más avanzado para gestionar libros con múltiples autores, reflejando con mayor precisión la información de la API de Gutendex.
* **Transformación a API REST o GUI:** Evolucionar la aplicación de consola a una API REST (usando `<span class="selected">spring-boot-starter-web</span>`) para permitir su integración con otras aplicaciones, o desarrollar una Interfaz Gráfica de Usuario (GUI) con JavaFX o Swing.
* **Reportes y Estadísticas Avanzadas:** Añadir funcionalidades para generar reportes más complejos sobre los datos almacenados, como el número de libros por idioma, por autor, o los autores más prolíficos, y permitir la ordenación por diferentes criterios.
* **Pruebas Automatizadas:** Incorporar tests unitarios y de integración para asegurar la calidad y el correcto funcionamiento de cada componente del código.

## 🤝 Contribuciones

**¡Las contribuciones y las ideas para mejorar LiterAlura son siempre bienvenidas! Si deseas contribuir:**

* **Puedes ****abrir un `<span class="selected">issue</span>`** en el repositorio para reportar errores, sugerir nuevas características o discutir ideas.
* **También puedes ****enviar un `<span class="selected">pull request</span>`** con tus propias implementaciones y mejoras al código.

## 📄 Licencia

**Este proyecto se distribuye bajo la ** **Licencia MIT** **. Consulta el archivo **`<span class="selected">LICENSE</span>` en la raíz del repositorio para obtener más detalles.
