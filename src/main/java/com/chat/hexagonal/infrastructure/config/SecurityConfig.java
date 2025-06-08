// src/main/java/com/chat/hexagonal/infrastructure/config/SecurityConfig.java
package com.chat.hexagonal.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Indica que esta clase contiene definiciones de beans de configuración
public class SecurityConfig {

    @Bean // Marca el método para que el objeto retornado sea un bean de Spring
    public PasswordEncoder passwordEncoder() {
        // Usa BCryptPasswordEncoder, que es un algoritmo de hashing fuerte y recomendado
        return new BCryptPasswordEncoder();
    }
}
