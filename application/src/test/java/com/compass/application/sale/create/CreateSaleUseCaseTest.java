package com.compass.application.sale.create;

import com.compass.application.UseCaseTest;
import com.compass.domain.product.Product;
import com.compass.domain.product.ProductGateway;
import com.compass.domain.product.ProductID;
import com.compass.domain.sale.SaleGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CreateSaleUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateSaleUseCase createSaleUseCase;

    @Mock
    private SaleGateway saleGateway;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(saleGateway, productGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsCreateSale_shouldReturnSaleId() {
        final var expectedProductSales = Arrays.asList(
                new CreateSaleCommand.ProductSale("product1", 5),
                new CreateSaleCommand.ProductSale("product2", 3)
        );

        final var aCommand = CreateSaleCommand.with(expectedProductSales);

        when(productGateway.findById(any())).thenReturn(Optional.of(mock(Product.class)));

        when(saleGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = createSaleUseCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(expectedProductSales.stream().map(CreateSaleCommand.ProductSale::getProductId).toList(), actualOutput.productIds());

        verify(saleGateway, times(1)).create(argThat(aSale ->
                Objects.equals(expectedProductSales.get(0).getProductId(), aSale.getProductsIds().get(0).getValue()) &&
                        Objects.nonNull(aSale.getId()) &&
                        Objects.nonNull(aSale.getCreatedAt()) &&
                        Objects.nonNull(aSale.getUpdatedAt())
        ));
    }

}
