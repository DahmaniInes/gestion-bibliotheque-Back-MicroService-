package com.noussa.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
@Override
public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/events/**")  // Cette ligne définit le pattern d'URL pour lequel CORS est activé
        .allowedOrigins("http://localhost:4200")  // URL de votre application Angular
        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Méthodes HTTP autorisées
        .allowedHeaders("*")  // Tous les headers sont autorisés
        .allowCredentials(true);  // Autorise l'envoi des credentials (cookies, headers d'authentification)
        }
        }
