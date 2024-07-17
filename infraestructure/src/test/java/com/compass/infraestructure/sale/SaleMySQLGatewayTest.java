package com.compass.infraestructure.sale;

import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.sale.Sale;
import com.compass.infraestructure.MySQLGatewayTest;
import com.compass.infraestructure.product.ProductMySQLGateway;
import com.compass.infraestructure.sale.persistence.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@MySQLGatewayTest
public class SaleMySQLGatewayTest {
    @Autowired
    private ProductMySQLGateway productGateway;

    @Autowired
    private SaleMySQLGateway saleGateway;

    @Autowired
    private SaleRepository saleRepository;

    @Test

    public void testDependeciesInject() {
        Assertions.assertNotNull(productGateway);
        Assertions.assertNotNull(saleGateway);
        Assertions.assertNotNull(saleRepository);
    }

    @Test
    public void givenAValidSale_whenCallsCreateSale_shouldPersistSale() {
        final var aProduct = productGateway.create(Product.newProduct("Cafe", "Cafe premium", true, 10.1, 10));

        final var expectedProducts = List.of(aProduct.getId());

        final var aSale = Sale.newSale(expectedProducts, 10);

        Assertions.assertEquals(0, saleRepository.count());

        final var actualSale = saleGateway.create(aSale);

        Assertions.assertEquals(1, saleRepository.count());

        Assertions.assertEquals(aSale.getId().getValue(), actualSale.getId().getValue());
        Assertions.assertEquals(expectedProducts, actualSale.getProductsIds());
        Assertions.assertEquals(aSale.getCreatedAt(), actualSale.getCreatedAt());
        Assertions.assertEquals(aSale.getUpdatedAt(), actualSale.getUpdatedAt());

        final var persistedSale = saleRepository.findById(actualSale.getId().getValue()).get();

        Assertions.assertEquals(aSale.getCreatedAt(), persistedSale.getCreatedAt());
        Assertions.assertEquals(aSale.getUpdatedAt(), persistedSale.getUpdatedAt());
        Assertions.assertEquals(expectedProducts, persistedSale.getProductIDS());

    }

    @Test
    public void givenAProductInSales_whenCallsFindByProductId_shouldReturnSales() {
        // Cria um produto
        Product createdProduct = productGateway.create(Product.newProduct("Cafe", "Cafe premium", true, 101.1, 10));
        ProductID productId = createdProduct.getId();
        List<ProductID> expectedProducts = List.of(productId);

        // Cria duas vendas com o produto
        Sale sale1 = Sale.newSale(expectedProducts, 10);
        Sale sale2 = Sale.newSale(expectedProducts, 10);
        saleGateway.create(sale1);
        saleGateway.create(sale2);

        // Verifica as vendas que contêm o produto
        boolean productFindInsale = saleGateway.findByProductId(productId);

        Assertions.assertTrue(productFindInsale);
        // Asserções


    }
    @Test
    public void givenAProductInSales_whenCallsFindAllSalesByProductId_shouldReturnSales() {
        // Cria um produto
        Product createdProduct = productGateway.create(Product.newProduct("Cafe", "Cafe premium", true, 101.1, 10));
        ProductID productId = createdProduct.getId();
        List<ProductID> expectedProducts = List.of(productId);

        // Cria duas vendas com o produto
        Sale sale1 = Sale.newSale(expectedProducts, 10);
        Sale sale2 = Sale.newSale(expectedProducts, 10);
        saleGateway.create(sale1);
        saleGateway.create(sale2);

        // Verifica as vendas que contêm o produto
        List<Sale> sales = saleGateway.findAllSalesByProductId(productId);

        // Asserções
        Assertions.assertEquals(2, sales.size(), "Deveria retornar duas vendas");
        Assertions.assertTrue(sales.stream().anyMatch(sale -> sale.getId().equals(sale1.getId())), "Deveria conter a venda 1");
        Assertions.assertTrue(sales.stream().anyMatch(sale -> sale.getId().equals(sale2.getId())), "Deveria conter a venda 2");
    }
}


//    @Test
//    public void givenAProduct_whenFindAllByProductId_shouldReturnSalesWithThatProduct() {
//        // Cria um produto
//        final var aProduct = productGateway.create(Product.newProduct("Cafe", "Cafe premium", true, 10.1));
//
//        // Cria uma venda com o produto
//        final var expectedProducts = List.of(aProduct.getId());
//        final var aSale = Sale.newSale(expectedProducts);
//        saleGateway.create(aSale);
//
//        // Busca todas as vendas que contêm o produto
//        final var salesWithProduct = saleGateway.findByProductId(aProduct.getId());
//
//        // Verifica se a venda criada está na lista de vendas retornadas
////        Assertions.assertTrue(salesWithProduct.stream().anyMatch(sale -> sale.getId().equals(aSale.getId())));
////
////        // Verifica se todas as vendas retornadas contêm o produto
////        for (Sale sale : salesWithProduct) {
////            Assertions.assertTrue(sale.getProductsIds().contains(aProduct.getId()));
////        }
//    }

