package com.compass.infraestructure;



import com.compass.application.product.create.CreateProductUseCase;
import com.compass.infraestructure.configuration.WebServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(WebServerConfig.class, args);

    }

}