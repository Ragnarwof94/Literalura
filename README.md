📚 LiterAlura: Cliente de API Gutendex en Java

Este proyecto implementa una aplicación de consola en Java 17 que actúa como un cliente para la API de Gutendex. Fue desarrollado para permitir a los usuarios buscar, registrar y gestionar información de libros y autores, almacenándola persistentemente en una base de datos local PostgreSQL.



🌟 Características Destacadas

🔍 Búsqueda Interactiva: Permite buscar libros por título en la API de Gutendex, mostrando múltiples resultados para que el usuario seleccione el libro deseado a registrar.



🌐 Filtrado por Idioma: Opción de refinar las búsquedas de la API por un código de idioma específico (ej. "es" para español, "en" para inglés, "de" para alemán).



💾 Persistencia Robusta: Almacena libros y autores en una base de datos PostgreSQL utilizando Spring Data JPA, asegurando la integridad de los datos y evitando duplicados.



✍️ Manejo Inteligente de Autores: Extrae y persiste la información detallada del autor (nombre, año de nacimiento, año de fallecimiento) de la API, gestionando correctamente los casos de datos inconsistentes o nulos.



📋 Consultas Avanzadas: Proporciona funcionalidades para listar todos los libros y autores registrados, buscar autores que estuvieron vivos en un rango de años específico, y filtrar libros por idioma.



💻 Interfaz de Consola Amigable: Toda la interacción con la aplicación se realiza a través de un menú sencillo e intuitivo en la línea de comandos.



❌ Gestión de Errores: Incluye manejo de excepciones para abordar problemas de comunicación con la API externa, errores de formato JSON y conflictos al guardar datos en la base de datos.



🛠️ Tecnologías Utilizadas

☕ Java 17+



🚀 Spring Boot (con spring-boot-starter-data-jpa y spring-boot-starter-validation)



🗄️ PostgreSQL



📦 Maven



📝 Gson (para deserialización JSON de la API)



🌐 Java 11+ HttpClient (para peticiones a la API)



📚 API Gutendex (como fuente de datos de libros)



🚀 Guía de Inicio Rápido

Sigue estos pasos para poner en marcha el proyecto LiterAlura en tu entorno local.



📌 Requisitos Previos

JDK 17+



Maven



IntelliJ IDEA (u otro IDE de tu preferencia)



PostgreSQL (servidor de base de datos en ejecución, puerto 5432 por defecto)



PgAdmin 4 (u otro cliente de base de datos para gestionar PostgreSQL, opcional)



1️⃣ Configuración de la Base de Datos

Crea la base de datos:



Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.



Crea una nueva base de datos con el nombre exacto literalura\_db. Asegúrate de que el propietario sea postgres (o tu usuario principal).



Configura las credenciales:



En tu proyecto, abre el archivo src/main/resources/application.properties.



Actualiza las siguientes líneas con la información de tu base de datos:



spring.datasource.url=jdbc:postgresql://localhost:5432/literalura\_db

spring.datasource.username=postgres

spring.datasource.password=YOUR\_DB\_PASSWORD\_HERE

spring.datasource.driver-class-name=org.postgresql.Driver



spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format\_sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect



¡Importante! Reemplaza YOUR\_DB\_PASSWORD\_HERE con la contraseña de tu usuario postgres en PostgreSQL.



2️⃣ Preparación del Proyecto en tu IDE

Clona el repositorio: Abre tu terminal (Git Bash, PowerShell, etc.) y ejecuta:



git clone https://github.com/tu\_usuario/LiterAlura.git

cd LiterAlura



(Asegúrate de reemplazar tu\_usuario con tu nombre de usuario real de GitHub).



Abre el proyecto en tu IDE:



Inicia IntelliJ IDEA y selecciona "Open" o "Abrir".



Navega a la carpeta LiterAlura que clonaste y ábrela.



El IDE debería reconocer el proyecto Maven y descargar automáticamente todas las dependencias.



Recarga y Reconstruye:



En tu IDE, ve a Build -> Clean Project.



Luego, Build -> Rebuild Project. Esto asegura que todos los componentes se compilen correctamente.



3️⃣ Ejecución de la Aplicación

Localiza la clase principal: Ve a src/main/java/com/literalura/literalura/LiterAluraApplication.java.



Ejecuta el método main: Haz clic derecho dentro del código de esta clase y selecciona "Run 'LiterAluraApplication.main()'".



La aplicación se iniciará en la consola de tu IDE, presentando un menú interactivo.



🗺️ Uso de la Aplicación (Desde la Consola)

Una vez que la aplicación esté en ejecución, interactúa con ella a través del menú de la consola, ingresando el número de la opción deseada:



Buscar libro por título: Permite buscar libros en la API de Gutendex. Tras introducir un título, tendrás la opción de filtrar por idioma y luego seleccionar un libro de la lista de resultados para guardarlo en tu base de datos local.



Listar libros registrados: Muestra una lista detallada de todos los libros que has guardado previamente en tu base de datos local.



Listar autores registrados: Presenta una lista de todos los autores únicos almacenados en tu base de datos, excluyendo cualquier entrada de "Desconocido".



Listar autores vivos en un determinado año: Ingresa un año y la aplicación te mostrará los autores registrados que, según sus años de nacimiento y fallecimiento, estaban vivos durante ese periodo.



Listar libros por idioma: Introduce un código de idioma (ej. "es", "en", "de") y la aplicación filtrará y mostrará los libros registrados en tu base de datos que coincidan con ese idioma.



Salir: Termina la ejecución de la aplicación.



💡 Posibles Mejoras Futuras

Soporte Multi-Autor: Implementar un modelo de datos más avanzado para gestionar libros con múltiples autores, reflejando con mayor precisión la información de la API de Gutendex.



Transformación a API REST o GUI: Evolucionar la aplicación de consola a una API REST (utilizando spring-boot-starter-web) para permitir su integración con otras aplicaciones, o desarrollar una Interfaz Gráfica de Usuario (GUI) con JavaFX o Swing.



Reportes y Estadísticas Avanzadas: Añadir funcionalidades para generar reportes más complejos sobre los datos almacenados, como el número de libros por idioma, por autor, o los autores más prolíficos, y permitir la ordenación por diferentes criterios.



Pruebas Automatizadas: Incorporar tests unitarios y de integración para asegurar la calidad y el correcto funcionamiento de cada componente del código.



🤝 Contribuciones

¡Las contribuciones y las ideas para mejorar LiterAlura son siempre bienvenidas! Si deseas contribuir:



Puedes abrir un issue en el repositorio para reportar errores, sugerir nuevas características o discutir ideas.



También puedes enviar un pull request con tus propias implementaciones y mejoras al código.



📄 Licencia

Este proyecto se distribuye bajo la Licencia MIT. Consulta el archivo LICENSE en la raíz del repositorio para obtener más detalles.

