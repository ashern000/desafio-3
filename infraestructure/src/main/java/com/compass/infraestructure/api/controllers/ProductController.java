package com.compass.infraestructure.api.controllers;

import com.compass.infraestructure.api.ProductAPI;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProductController implements ProductAPI {
    @Override
    public ResponseEntity<?> createProduct() {
        return null;
    }

    @Override
    public List<?> listProducts() {
        return List.of();
    }
}
