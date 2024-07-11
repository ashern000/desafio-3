package com.compass.infraestructure.sale.persistence;

import com.compass.domain.product.Product;
import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.domain.sale.SaleID;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "Sale")
@Table(name = "sales")
public class SaleJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleProductJpaEntity> products;

    public SaleJpaEntity() {}

    private SaleJpaEntity(
            final String id,
            final double totalPrice,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.products = new HashSet<>();
    }

    public static SaleJpaEntity from(final Sale aSale) {
        final var anEntity = new SaleJpaEntity(
                aSale.getId().getValue(),
                aSale.getTotalPrice(),
                aSale.getCreatedAt(),
                aSale.getUpdatedAt()
        );
        aSale.getProductsIds().forEach(anEntity::addProduct);
        return anEntity;
    }

    public Sale toDomain() {
        return Sale.with(SaleID.from(getId()), getProductIDS(),getTotalPrice(), getCreatedAt(), getUpdatedAt());
    }

    public List<ProductID> getProductIDS() {
        return getProducts().stream().map(it -> ProductID.from(it.getId().getProductId())).toList();
    }

    private void addProduct(final ProductID anId){
        this.products.add(SaleProductJpaEntity.from(this, anId));
    }

    private void removeProduct(final ProductID anId){
        this.products.remove(SaleProductJpaEntity.from(this, anId));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<SaleProductJpaEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<SaleProductJpaEntity> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

