package com.literatura.literatura; // Asegúrate de que este sea el paquete correcto

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // Esta anotación ahora buscará la clase principal LiterAluraApplication
class LiterAluraApplicationTests {

    @Test
    void contextLoads() {
        // Este test verifica si el contexto de Spring Boot se carga correctamente.
        // Si el contexto no se carga, este test fallará y generará el error del surefire plugin.
    }

}