package com.compass.infraestructure.product;


import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.infraestructure.product.persistence.ProductJpaEntity;
import com.compass.infraestructure.product.persistence.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductMySQLGateway implements ProductGateway {

    private final ProductRepository repository;

    public ProductMySQLGateway(final ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public Product create(final Product product) {
        return save(product);
    }

    @Override
    public void delete(ProductID anId) {

    }

    @Override
    public Product update(Product product) {
        return save(product);
    }

    @Override
    public Optional<Product> findById(ProductID anId) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<ProductJpaEntity> productEntities = repository.findAll();

        // Convert each ProductJpaEntity to a Product and collect them into a list
        List<Product> products = productEntities.stream()
                .map(ProductJpaEntity::toDomain)
                .collect(Collectors.toList());

        return products;
    }

    private Product save(final Product product) {
        return this.repository.save(ProductJpaEntity.from(product)).toDomain();
    }
}
