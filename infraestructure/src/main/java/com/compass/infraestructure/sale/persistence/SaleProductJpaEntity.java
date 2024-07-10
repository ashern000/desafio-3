package com.compass.infraestructure.sale.persistence;

import com.compass.infraestructure.product.persistence.ProductJpaEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sale_products")
public class SaleProductJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @Column(name = "quantity")
    private int quantity;

    // getters and setters
}

