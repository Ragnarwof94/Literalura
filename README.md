📚 LiterAlura: Cliente de API Gutendex en Java
Este proyecto implementa una aplicación de consola en Java 17 que interactúa con la API de Gutendex para buscar y gestionar información de libros y autores. Permite a los usuarios buscar libros por título (con opciones de filtrado por idioma), registrar la información en una base de datos local (PostgreSQL) y consultar diversos datos almacenados sobre libros y autores.

🌟 Características
Búsqueda y Selección de Libros: Permite buscar libros por título en la API de Gutendex, mostrando múltiples resultados para que el usuario elija cuál registrar.

Filtrado por Idioma: Opción de filtrar los resultados de búsqueda de la API por un código de idioma específico (ej. "es", "en").

Consulta de Detalles: Muestra información relevante de los libros encontrados (título, autor, idiomas, número de descargas).

Persistencia de Datos: Guarda y consulta libros y autores en una base de datos local (PostgreSQL) usando Spring Data JPA.

Gestión de Autores: Identifica y persiste autores, evitando duplicados y gestionando casos donde la API no proporciona un nombre de autor válido.

Interfaz de Consola: Interacción sencilla a través de un menú en la línea de comandos para todas las funcionalidades.

Manejo de Errores: Incluye gestión de excepciones para problemas de comunicación con la API, formato de datos y duplicados en la base de datos.

🛠️ Tecnologías Utilizadas
Java 17: Lenguaje de programación principal.

Spring Boot: Framework para el desarrollo de aplicaciones basadas en Spring, facilitando la configuración y el despliegue.

spring-boot-starter-data-jpa: Para la capa de persistencia con JPA.

spring-boot-starter-validation: Para la validación de datos (si se añaden DTOs).

PostgreSQL: Sistema de gestión de bases de datos relacionales para persistencia local.

Maven: Herramienta de gestión de dependencias y construcción del proyecto.

Gson: Librería de Google para la serialización y deserialización de objetos Java a/desde JSON.

Java 11+ HttpClient: API nativa de Java para realizar solicitudes HTTP a la API externa.

API Gutendex: API pública que proporciona un catálogo de libros y metadatos.

🚀 Cómo Empezar
Sigue estos pasos para clonar, configurar y ejecutar la aplicación en tu entorno local.

Requisitos Previos
JDK 17 o superior.

Maven (generalmente incluido con los IDEs modernos como IntelliJ IDEA).

IntelliJ IDEA (o tu IDE de preferencia).

PostgreSQL instalado y ejecutándose localmente (puerto por defecto 5432).

PgAdmin 4 (o cualquier cliente PostgreSQL) para gestionar la base de datos.

1. Configuración de la Base de Datos
Crea la base de datos:

Abre PgAdmin 4 y conéctate a tu servidor PostgreSQL.

Crea una nueva base de datos llamada literalura_db. Asegúrate de que el propietario sea postgres (o tu usuario principal).

Configura las credenciales en application.properties:

Crea (si no existe) o abre src/main/resources/application.properties en tu proyecto.

Añade las siguientes líneas, reemplazando YOUR_DB_PASSWORD_HERE con tu contraseña de PostgreSQL:

spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres
spring.datasource.password=YOUR_DB_PASSWORD_HERE
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

2. Configuración del Proyecto en tu IDE
Clona el repositorio:

git clone https://github.com/tu_usuario/LiterAlura.git
cd LiterAlura

(Reemplaza tu_usuario con tu nombre de usuario de GitHub).

Abrir el proyecto en tu IDE:

Abre tu IDE (ej. IntelliJ IDEA).

Selecciona "Open" o "Abrir" y navega hasta la carpeta LiterAlura que acabas de clonar/crear.

Tu IDE debería detectar automáticamente que es un proyecto Maven e importar las dependencias.

Recarga las dependencias de Maven: Asegúrate de que tu IDE descargue todas las librerías necesarias (especificadas en pom.xml).

Limpia y reconstruye el proyecto:

En tu IDE, ve a Build -> Clean Project.

Luego, Build -> Rebuild Project.

3. Ejecutar la Aplicación
En tu IDE, navega a la clase principal: LiterAluraApplication.java (ubicada en src/main/java/com/literalura/literalura/).

Haz clic derecho en el método main y selecciona "Run 'LiterAluraApplication.main()'".

La aplicación se ejecutará en la ventana de la consola de tu IDE, mostrando el menú interactivo.

📊 Flujo de la Aplicación
flowchart TD
    style A fill:#fdf6e3,stroke:#268bd2,stroke-width:2px
    style B fill:#eee8d5,stroke:#2aa198
    style C fill:#eee8d5,stroke:#859900
    style D fill:#eee8d5,stroke:#b58900
    style E fill:#eee8d5,stroke:#cb4b16
    style F fill:#eee8d5,stroke:#6c71c4
    style G fill:#eee8d5,stroke:#d33682
    style H fill:#eee8d5,stroke:#268bd2
    style I fill:#eee8d5,stroke:#2aa198
    style J fill:#eee8d5,stroke:#859900
    style K fill:#eee8d5,stroke:#b58900

    A[📚 **LiterAlura: Cliente de API Gutendex**] --> B[🔧 **Requisitos Previos**]
    B --> B1[JDK 17+ ☕]
    B --> B2[Maven 📦]
    B --> B3[IntelliJ IDEA u otro IDE 🖥️]
    B --> B4[PostgreSQL en ejecución 🗄️]
    B --> B5[PgAdmin u otro cliente]

    A --> C[1️⃣ **Configurar Base de Datos**]
    C --> C1[Archivo: src/main/resources/application.properties]
    C1 --> C2[spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db]
    C1 --> C3[spring.datasource.username=postgres]
    C1 --> C4[spring.datasource.password=tu_contraseña]
    C1 --> C5[spring.jpa.hibernate.ddl-auto=update]
    C1 --> C6[spring.jpa.show-sql=true]

    A --> D[2️⃣ **Clonar y Configurar Proyecto**]
    D --> D1[git clone https://github.com/tu_usuario/LiterAlura.git]
    D --> D2[cd LiterAlura]
    D --> D3[Abrir proyecto en IDE 🖥️]
    D --> D4[Dejar que Maven descargue dependencias 📦]
    D --> D5[Limpia y reconstruye el proyecto 🔨]

    A --> E[3️⃣ **Ejecutar la Aplicación**]
    E --> E1[Abrir LiterAluraApplication.java]
    E --> E2[Ejecutar método main ▶️]
    E --> E3[Usar menú interactivo 📝]

    E3 --> F[📋 **Opciones de Menú**]
    F --> F1[🔍 Buscar libro por título (filtrar por idioma)]
    F --> F2[📖 Listar libros registrados]
    F --> F3[✍️ Listar autores registrados]
    F --> F4[👤 Listar autores vivos en un año específico]
    F --> F5[🌐 Listar libros por idioma]
    F --> F6[❌ Salir de la aplicación]

    A --> G[💾 **Persistencia Local**]
    G --> G1[PostgreSQL con Spring Data JPA]
    G --> G2[Evita duplicados de libros y autores]
    G --> G3[Manejo de autores “Desconocido”]

    A --> H[🧩 **Modelo de Datos**]
    H --> H1[BOOKS]
    H1 --> H1a[id PK]
    H1 --> H1b[title]
    H1 --> H1c[download_count]
    H --> H2[AUTHORS]
    H2 --> H2a[id PK]
    H2 --> H2b[name]
    H2 --> H2c[birth_year]
    H2 --> H2d[death_year]
    H --> H3[BOOK_LANGUAGES]
    H3 --> H3a[book_id FK]
    H3 --> H3b[language]
    AUTHORS ||--o{ BOOKS : "escribe"
    BOOKS ||--o{ BOOK_LANGUAGES : "se traduce a"

    A --> I[💡 **Posibles Mejoras**]
    I --> I1[Manejo de múltiples autores por libro 📚]
    I --> I2[Autenticación/seguridad multiusuario 🔐]
    I --> I3[Reportes estadísticos 📊]
    I --> I4[Documentación automática Swagger/OpenAPI 📝]
    I --> I5[Tests unitarios e integración ✅]

    A --> J[🤝 **Contribuciones**]
    J --> J1[Abrir issue o pull request]

    A --> K[📄 **Licencia**]
    K --> K1[MIT]

🗺️ Uso de la Aplicación (Consola)
Una vez que la aplicación esté corriendo, verás un menú en la consola. Sigue las opciones para interactuar:

Buscar libro por título: Busca un libro en la API de Gutendex. Te preguntará si deseas filtrar por idioma y luego te mostrará una lista para que elijas cuál registrar.

Listar libros registrados: Muestra los libros que ya has guardado en tu base de datos local.

Listar autores registrados: Muestra todos los autores únicos (excluyendo "Desconocido") guardados en tu base de datos local.

Listar autores vivos en un determinado año: Permite consultar autores que estaban vivos en un año específico según sus años de nacimiento y fallecimiento, excluyendo "Desconocido".

Listar libros por idioma: Filtra y muestra los libros guardados en la base de datos por un código de idioma (ej. "es", "en").

Salir: Termina la ejecución de la aplicación.

💡 Posibles Mejoras
Manejo de Múltiples Autores por Libro: Aunque la API puede devolver varios autores, actualmente se asocia solo el primero. Se podría mejorar la lógica para guardar y gestionar múltiples autores por libro.

Interfaz Gráfica o API REST: Convertir la aplicación a una interfaz gráfica de usuario (GUI) o exponer las funcionalidades como una API REST (utilizando spring-boot-starter-web) para permitir la interacción desde otras aplicaciones o Insomnia.

Manejo de Errores de API más robusto: Ofrecer mensajes más amigables si la API Gutendex no responde o devuelve errores.

Tests Unitarios e Integración: Implementar pruebas para asegurar la correcta funcionalidad de cada componente.

🤝 Contribuciones
¡Las contribuciones son bienvenidas! Si tienes ideas para mejorar LiterAlura, no dudes en abrir un "issue" o enviar un "pull request".

📄 Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.