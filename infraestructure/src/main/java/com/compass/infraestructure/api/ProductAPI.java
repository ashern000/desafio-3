package com.compass.infraestructure.api;

import com.compass.infraestructure.product.models.CreateProductApiInput;
import com.compass.infraestructure.product.models.DeleteProductApiInput;
import com.compass.infraestructure.product.models.UpdateProductApiInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/products")
@Tag(name = "Products")
public interface ProductAPI {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?> createProduct(@RequestHeader(value="Authorization") String token,@RequestBody CreateProductApiInput input);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List a Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    List<?> listProducts(@RequestHeader(value="Authorization") String token);

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Updated successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })
    ResponseEntity<?>  updateProduct(@RequestHeader(value="Authorization") String token,@RequestBody UpdateProductApiInput input);

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Delete a Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deleted successfully"),
            @ApiResponse(responseCode = "422", description = "Unprocessable error"),
            @ApiResponse(responseCode = "500", description = "An internal server error was thrown")
    })

    ResponseEntity<?> deleteProduct(@RequestHeader(value="Authorization") String token, @RequestBody DeleteProductApiInput input);
}
