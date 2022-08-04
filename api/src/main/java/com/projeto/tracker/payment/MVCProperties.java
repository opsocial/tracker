package com.projeto.tracker.payment;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//essa classe é para evitar o erro de CORS quando fazer a requisição do front
@Configuration
public class MVCProperties implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowedOrigins("*").
                allowedOrigins("https://checkout.stripe.com").
                allowedOrigins("https://m.stripe.network").
                allowedOrigins("http://localhost:4200").
                allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS");
    }

}
