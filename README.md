📚 LiterAlura: Cliente de API Gutendex en Java
Este proyecto implementa una aplicación de consola en Java 17 que interactúa con la API de Gutendex para buscar y gestionar información de libros y autores. Permite a los usuarios buscar libros, registrar la información en una base de datos local y consultar diversos datos almacenados.

🌟 Características
Búsqueda de Libros: Permite buscar libros por título utilizando la API de Gutendex.

Consulta de Detalles: Muestra información relevante de los libros encontrados (título, autor, idiomas, número de descargas).

Interfaz de Consola: Interacción sencilla a través de un menú en la línea de comandos.

Persistencia de Datos: Guarda y consulta libros y autores en una base de datos local (PostgreSQL) usando Spring Data JPA.

Gestión de Datos Locales:

Listar todos los libros registrados en la base de datos.

Listar todos los autores registrados en la base de datos.

Listar autores vivos en un determinado año.

Listar libros registrados en un idioma específico.

Manejo de Errores: Incluye gestión de excepciones para problemas de comunicación con la API y formato de datos.

🛠️ Tecnologías Utilizadas
Java 17: Lenguaje de programación principal.

Spring Boot: Framework para el desarrollo de aplicaciones basadas en Spring, facilitando la configuración y el despliegue.

spring-boot-starter-data-jpa: Para la capa de persistencia con JPA.

PostgreSQL: Sistema de gestión de bases de datos relacionales (para persistencia local).

Maven: Herramienta de gestión de dependencias y construcción del proyecto.

Gson: Librería de Google para la serialización y deserialización de objetos Java a/desde JSON.

Java 11+ HttpClient: API nativa de Java para realizar solicitudes HTTP a la API externa.

API Gutendex: API pública que proporciona un catálogo de libros y metadatos.

🚀 Cómo Empezar
Sigue estos pasos para clonar, configurar y ejecutar la aplicación en tu entorno local.

Requisitos Previos
Asegúrate de tener instalado lo siguiente:

JDK 17 o superior.

Maven (generalmente incluido con los IDEs modernos como IntelliJ IDEA).

IntelliJ IDEA (o tu IDE de preferencia).

PostgreSQL instalado y ejecutándose localmente.

PgAdmin 4 (o cualquier cliente PostgreSQL) para gestionar la base de datos.

1. Configuración de la Base de Datos
Crea la base de datos:

Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.

Crea una nueva base de datos. Puedes llamarla, por ejemplo, literalura_db.

Configura las credenciales en application.properties:

Crea (si no existe) o abre src/main/resources/application.properties en tu proyecto.

Añade las siguientes líneas, reemplazando con tus credenciales de PostgreSQL:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres # Tu usuario de PostgreSQL
spring.datasource.password=tu_contraseña_real_aqui # Tu contraseña de PostgreSQL
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update # Para que Hibernate cree/actualice las tablas
spring.jpa.show-sql=true # Para ver las sentencias SQL en consola

2. Configuración del Proyecto en tu IDE
Clona el repositorio (o crea el proyecto y copia los archivos si es tu primer setup):

git clone https://github.com/tu_usuario/LiterAlura.git
cd LiterAlura

(Reemplaza la URL con la de tu propio repositorio si ya la tienes en GitHub).

Abrir el proyecto en tu IDE:

Abre tu IDE (ej. IntelliJ IDEA).

Selecciona "Open" o "Abrir" y navega hasta la carpeta LiterAlura que acabas de clonar/crear.

Tu IDE debería detectar automáticamente que es un proyecto Maven e importar las dependencias.

Recarga las dependencias de Maven: Asegúrate de que tu IDE descargue todas las librerías necesarias (especificadas en pom.xml, como Gson y las de Spring Boot). Busca el icono de Maven para "Reload All Maven Projects" o una opción similar.

Limpia y reconstruye el proyecto:

En tu IDE, ve a Build -> Clean Project.

Luego, Build -> Rebuild Project.

3. Ejecutar la Aplicación
En tu IDE, navega a la clase principal: LiterAluraApplication.java (ubicada en src/main/java/com/literalura/literalura/).

Haz clic derecho en el método main y selecciona "Run 'LiterAluraApplication.main()'" (o el botón de "Play" similar).

La aplicación se ejecutará en la ventana de la consola de tu IDE. Sigue las instrucciones del menú para interactuar.

🗺️ Uso de la Aplicación (Consola)
Una vez que la aplicación esté corriendo, verás un menú en la consola. Sigue las opciones para interactuar:

Buscar libro por título: Busca un libro en la API de Gutendex.

Listar libros registrados: Muestra los libros que ya has guardado en tu base de datos local.

Listar autores registrados: Muestra todos los autores únicos guardados en tu base de datos local.

Listar autores vivos en un determinado año: Permite consultar autores que estaban vivos en un año específico según sus años de nacimiento y fallecimiento.

Listar libros por idioma: Filtra y muestra los libros guardados en la base de datos por un código de idioma (ej. "es", "en").

Salir: Termina la ejecución de la aplicación.

💡 Posibles Mejoras
Validación de Entrada Mejorada: Reforzar la validación de la entrada del usuario en la consola para todas las opciones del menú.

Manejo de Múltiples Autores: La API puede devolver varios autores para un libro; actualmente solo se usa el primero. Se podría mejorar la lógica para manejar múltiples autores de manera más robusta.

Manejo de Errores de API más robusto: Ofrecer mensajes más amigables si la API Gutendex no responde o devuelve errores.

🤝 Contribuciones
¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar LiterAlura, no dudes en abrir un "issue" o enviar un "pull request".

📄 Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.