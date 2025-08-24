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
