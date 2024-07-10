package com.compass.infraestructure.sale.persistence;

import com.compass.infraestructure.product.persistence.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SaleRepository extends JpaRepository<SaleJpaEntity, String> {

    @Query("SELECT s.sale FROM SaleProductJpaEntity s WHERE s.id.productId = :productId")
    List<SaleJpaEntity> findSalesByProductId(@Param("productId") String productId);

    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN true ELSE false END FROM SaleProductJpaEntity sp WHERE sp.id.productId = :productId")
    boolean existsProductInSale(@Param("productId") String productId);


}