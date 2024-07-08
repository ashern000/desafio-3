package com.compass.application.product.retrieve.get;

import com.compass.application.product.UseCaseTest;
import com.compass.domain.product.ProductGateway;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

public class GetProductUseCaseTest implements UseCaseTest {

    @InjectMocks
    private DefaultGetProductUseCase defaultGetProductUseCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    
}
