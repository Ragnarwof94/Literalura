📚 LiterAlura: Cliente de API Gutendex en Java
Este proyecto implementa una aplicación de consola en Java 17 diseñada para interactuar con la API de Gutendex. Permite a los usuarios buscar, registrar y gestionar información de libros y autores, almacenándola en una base de datos local PostgreSQL.

🌟 Características Destacadas
Búsqueda Interactiva: Busca libros por título en la API de Gutendex, ofreciendo múltiples resultados para que el usuario seleccione y registre el deseado.

Filtrado por Idioma: Incluye una opción para filtrar los resultados de búsqueda de la API por un código de idioma específico (ej. "es", "en", "de").

Persistencia Robusta: Guarda libros y autores en una base de datos PostgreSQL mediante Spring Data JPA, evitando duplicados y gestionando la correcta asociación de autores.

Manejo Inteligente de Autores: Extrae y persiste la información del autor de la API, incluyendo sus años de nacimiento y fallecimiento, y gestiona los casos donde esta información es inconsistente o nula.

Consultas Avanzadas: Permite listar todos los libros y autores registrados, buscar autores que estuvieron vivos en un año específico, y filtrar libros por idioma.

Interfaz de Consola Amigable: Toda la interacción se realiza a través de un menú sencillo y claro en la línea de comandos.

Gestión de Errores: Implementa un manejo de excepciones para abordar problemas de red, formato JSON de la API y conflictos de datos en la base de datos.

🛠️ Tecnologías Utilizadas
Java 17+: Lenguaje principal de desarrollo.

Spring Boot: Framework que facilita la creación de aplicaciones Java autónomas y robustas.

spring-boot-starter-data-jpa: Para la capa de persistencia con el Java Persistence API (JPA).

spring-boot-starter-validation: Para la validación de objetos (si se utilizan DTOs para la entrada de datos).

PostgreSQL: Sistema de gestión de bases de datos relacionales para el almacenamiento de datos.

Maven: Herramienta para la automatización de la construcción y la gestión de dependencias del proyecto.

Gson: Librería de Google para la conversión eficiente de objetos Java a JSON y viceversa.

Java 11+ HttpClient: Cliente HTTP nativo de Java para la comunicación con APIs externas.

API Gutendex: Servicio RESTful público que proporciona acceso a los metadatos de Project Gutenberg.

🚀 Guía de Inicio Rápido
Sigue estos pasos para poner en marcha el proyecto LiterAlura en tu entorno local.

Requisitos Previos
Asegúrate de tener instaladas las siguientes herramientas:

Java Development Kit (JDK) 17 o una versión posterior ☕.

Apache Maven 📦.

Un Entorno de Desarrollo Integrado (IDE) como IntelliJ IDEA 🖥️.

Un servidor PostgreSQL en ejecución localmente (puerto 5432 por defecto) 🗄️.

PgAdmin 4 o un cliente similar para la gestión de bases de datos.

1. Configuración de la Base de Datos
Crea la base de datos:

Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.

Crea una nueva base de datos con el nombre exacto literalura_db. Asigna postgres como propietario.

Configura las credenciales:

En tu proyecto, abre el archivo src/main/resources/application.properties.

Actualiza las siguientes líneas con la información de tu base de datos:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_DB_PASSWORD_HERE
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

¡Importante! Reemplaza YOUR_DB_PASSWORD_HERE con la contraseña de tu usuario postgres en PostgreSQL.

2. Preparación del Proyecto en tu IDE
Clona el repositorio: Abre tu terminal (Git Bash, PowerShell, etc.) y ejecuta:

git clone https://github.com/tu_usuario/LiterAlura.git
cd LiterAlura

(Asegúrate de reemplazar tu_usuario con tu nombre de usuario real de GitHub).

Abre el proyecto en tu IDE:

Inicia IntelliJ IDEA y selecciona "Open" o "Abrir".

Navega a la carpeta LiterAlura que clonaste y ábrela.

El IDE debería reconocer el proyecto Maven y descargar automáticamente todas las dependencias.

Recarga y Reconstruye:

En tu IDE, ve a Build -> Clean Project.

Luego, Build -> Rebuild Project. Esto asegura que todos los componentes se compilen correctamente.

3. Ejecución de la Aplicación
Localiza la clase principal: Ve a src/main/java/com/literalura/literalura/LiterAluraApplication.java.

Ejecuta el método main: Haz clic derecho dentro del código de esta clase y selecciona "Run 'LiterAluraApplication.main()'".

La aplicación se iniciará en la consola de tu IDE, presentando un menú interactivo.

🗺️ Uso de la Aplicación (Desde la Consola)
Una vez que la aplicación esté en ejecución, interactúa con ella a través del menú de la consola:

Buscar libro por título: Ingresa un título de libro. La aplicación consultará la API de Gutendex, te permitirá filtrar por idioma y te mostrará una lista de hasta 10 opciones para que selecciones el libro deseado a registrar.

Listar libros registrados: Muestra todos los libros que has guardado previamente en tu base de datos local, con sus detalles completos.

Listar autores registrados: Presenta una lista de todos los autores únicos almacenados en tu base de datos, excluyendo cualquier entrada de "Desconocido".

Listar autores vivos en un determinado año: Ingresa un año y la aplicación te mostrará los autores que, según sus años de nacimiento y fallecimiento, estaban vivos en ese periodo.

Listar libros por idioma: Ingresa un código de idioma (ej. "es", "en", "de") y la aplicación filtrará y mostrará los libros registrados en ese idioma.

Salir: Finaliza la ejecución de la aplicación.

💡 Posibles Mejoras Futuras
Soporte Multi-Autor: Implementar un modelo de datos más complejo para gestionar múltiples autores por libro, reflejando con mayor precisión los datos de la API.

Transformación a API REST o GUI: Evolucionar la aplicación de consola a una API REST (usando spring-boot-starter-web) para permitir su integración con otras aplicaciones, o desarrollar una Interfaz Gráfica de Usuario (GUI) con JavaFX o Swing.

Reportes y Estadísticas: Añadir funcionalidades para generar reportes sobre los datos almacenados, como el número de libros por idioma, por autor, o los autores más prolíficos.

Pruebas Automatizadas: Incorporar tests unitarios y de integración para asegurar la calidad y el correcto funcionamiento del código.

🤝 Contribuciones
¡Las contribuciones son siempre bienvenidas! Si tienes ideas para mejorar o expandir LiterAlura, no dudes en:

Abrir un issue en el repositorio para reportar errores o sugerir nuevas características.

Enviar un pull request con tus propias implementaciones y mejoras.

📄 Licencia
Este proyecto se distribuye bajo la Licencia MIT. Puedes encontrar los detalles completos en el archivo LICENSE en la raíz del repositorio.