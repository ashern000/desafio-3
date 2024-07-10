package com.compass.infraestructure.configuration;

import com.compass.application.product.create.CreateProductUseCase;
import com.compass.application.product.create.DefaulCreateProductUseCase;
import com.compass.application.product.retrieve.get.DefaultGetProductUseCase;
import com.compass.application.product.retrieve.get.GetProductUseCase;
import com.compass.application.product.retrieve.list.DefaultListProductUseCase;
import com.compass.application.product.retrieve.list.ListProductUseCase;
import com.compass.application.product.update.DefaultUpdateProductUseCase;
import com.compass.application.product.update.UpdateProductUseCase;
import com.compass.domain.product.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

    private final ProductGateway productGateway;

    public UseCasesConfig(final ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new DefaulCreateProductUseCase(productGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new DefaultUpdateProductUseCase(productGateway);
    }

    @Bean
    public GetProductUseCase getProductUseCase(){
        return new DefaultGetProductUseCase(productGateway);
    }

    @Bean
    public ListProductUseCase listProductUseCase() {
        return new DefaultListProductUseCase(productGateway);
    }
}
