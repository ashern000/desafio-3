package com.compass.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductGateway {
    Product create(Product product);

    void delete(ProductID anId);

    Product update(Product product);

    Optional<Product> findById(ProductID anId);

    Optional<Product> findByName(String name);

    List<Product> findAll();
}
