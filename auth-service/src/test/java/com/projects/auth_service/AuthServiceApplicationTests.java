package com.projects.auth_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.datasource.username=sa",
		"spring.datasource.password=",
		"spring.jpa.hibernate.ddl-auto=create-drop",
		"spring.flyway.enabled=false",
		"security.jwt.secret=test-secret-key-for-context-load-only",
		"security.jwt.issuer=auth-service",
		"security.jwt.expiration-minutes=60",
		"security.internal-api-key=test-key-for-context-load-only"
})
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
