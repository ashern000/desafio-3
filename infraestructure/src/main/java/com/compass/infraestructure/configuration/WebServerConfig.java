package com.compass.infraestructure.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.compass")
@OpenAPIDefinition(info = @Info(title = "API Documentation", version = "1.0", description = "Documentation for My API"))
public class WebServerConfig {
}
