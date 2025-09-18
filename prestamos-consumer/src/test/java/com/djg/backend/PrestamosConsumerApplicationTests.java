package com.djg.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Usa el perfil de test con H2
class PrestamosConsumerApplicationTests {

	@Test
	void contextLoads() {
		// Test básico para verificar que el contexto de Spring se carga correctamente
	}

}
