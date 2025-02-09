package com.trabajo1.ApiNombres.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Aplica a todas las rutas que empiezan con /api/
                        .allowedOrigins("http://localhost:5173")  // Permitir el front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*")  // Permitir todos los headers
                        .allowCredentials(true); // Permitir credenciales (si usas cookies o auth)
            }
        };
    }
}
