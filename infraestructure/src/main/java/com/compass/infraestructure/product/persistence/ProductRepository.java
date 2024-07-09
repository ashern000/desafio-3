package com.compass.infraestructure.product.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductJpaEntity, String> {
}
