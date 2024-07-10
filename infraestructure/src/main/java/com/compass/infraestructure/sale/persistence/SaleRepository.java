package com.compass.infraestructure.sale.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<SaleJpaEntity,String> {
}
