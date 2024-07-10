package com.compass.infraestructure.api;

import com.compass.application.product.create.CreateProductCommand;
import com.compass.application.product.create.CreateProductOutput;
import com.compass.application.product.create.CreateProductUseCase;
import com.compass.domain.product.ProductID;
import com.compass.domain.validation.handler.Notification;
import com.compass.infraestructure.ControllerTest;
import com.compass.infraestructure.product.models.CreateProductApiInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.API;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ControllerTest(controllers = ProductAPI.class)
public class ProductAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateProductUseCase createProductUseCase;

    @Test
    public void givenAValidCommand_whenCallsCreateProduct_shouldReturnProductId() throws Exception {
        final var expectedName = "Cafe";
        final var expectedDescription = "Cafe premium";
        final var expectedPrice = 101.1;
        final var expectedIsActive = true;

        final var aInput = new CreateProductApiInput(expectedName, expectedDescription,expectedIsActive,expectedPrice);

        Mockito.when(createProductUseCase.execute(any())).thenReturn(
                API.Right(CreateProductOutput.from("123"))
        );

        final var request = post("/products").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(aInput));

        this.mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.header().string("Location", "/products/123")
        );

        Mockito.verify(createProductUseCase, Mockito.times(1)).execute(argThat(cmd ->
                        Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive()) &&
                        Objects.equals(expectedPrice, cmd.price())
                ));

    }

    @Test
    public void givenAnInvalidName_whenCallsCreateProduct_thenShouldReturnDomainException() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "Cafe premium";
        final var expectedPrice = 101.1;
        final var expectedIsActive = true;

        final var aInput = new CreateProductApiInput(expectedName, expectedDescription,expectedIsActive,expectedPrice);

        Mockito.when(createProductUseCase.execute(any())).thenReturn(
                API.Left(Notification.create(new Error("'name' should not be null")))
        );

        final var request = post("/products").contentType(MediaType.APPLICATION_JSON).content(this.mapper.writeValueAsString(aInput));

        this.mockMvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpectAll(
                MockMvcResultMatchers.status().isUnprocessableEntity(),
                MockMvcResultMatchers.header().string("Location", Matchers.nullValue()),
                MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.equalTo("'name' should not be null"))
        );

        Mockito.verify(createProductUseCase, Mockito.times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name()) &&
                        Objects.equals(expectedDescription, cmd.description()) &&
                        Objects.equals(expectedIsActive, cmd.isActive()) &&
                        Objects.equals(expectedPrice, cmd.price())
        ));

    }
}
