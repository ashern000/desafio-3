package com.compass.application.product.retrieve.list;

import com.compass.application.product.UseCaseTest;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListProductUseCase defaultListUseCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    public void givenAValidListOfProducts_whenCallsUseCase() {
        final var products = List.of(
                Product.newProduct("Cafe", "Cafe premium", true, 10.1),
                Product.newProduct("Cha", "Cha verde", true, 11.1)
        );
        final var productOutputs = products.stream().map(ListProductOutput::from).toList();
        Mockito.when(productGateway.findAll()).thenReturn(products);

        final var actualResult = defaultListUseCase.execute();

        Assertions.assertEquals(productOutputs, actualResult);
    }

    @Test

    public void givenAnEmptyListOfProducts_whenCallUseCase() {
        Mockito.when(productGateway.findAll()).thenReturn(Collections.emptyList());

        final var actualResult = defaultListUseCase.execute();

        Assertions.assertTrue(actualResult.isEmpty());
    }
}
