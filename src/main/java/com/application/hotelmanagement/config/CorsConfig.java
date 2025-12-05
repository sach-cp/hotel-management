package com.application.hotelmanagement.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsConfig implements WebMvcConfigurer {

    private List<String> allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Allowed origins: {}", allowedOrigins);
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins.toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
