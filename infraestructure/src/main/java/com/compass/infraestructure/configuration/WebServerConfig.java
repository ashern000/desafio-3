package com.compass.infraestructure.configuration;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.compass")
@EnableCaching
public class WebServerConfig {
}
