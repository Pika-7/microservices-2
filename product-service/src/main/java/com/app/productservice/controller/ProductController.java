package com.app.productservice.controller;

import com.app.productservice.dto.ApiResponseDto;
import com.app.productservice.dto.ProductDto;
import com.app.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @Operation(description = "Adding a product based on the categoryId",
            responses = {
                    @ApiResponse(responseCode = "201", ref = "createResponse"),
                    @ApiResponse(responseCode = "400", ref = "badRequestResponse"),
                    @ApiResponse(responseCode = "500", ref = "internalServerResponse"),
            })
    @PostMapping("/{id}")
    public ResponseEntity<ApiResponseDto> addProduct(@RequestBody @Valid
                                                     ProductDto productDto, @PathVariable("id") String categoryId) {
        return new ResponseEntity<>(productService.addProduct(productDto, categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsOfCategory(@PathVariable("id") String categoryId) {
        return ResponseEntity.ok(productService.getAllProductsOfCategory(categoryId));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<ApiResponseDto> updateProduct(@RequestBody @Valid ProductDto productDto, @PathVariable("id") String productId) {
        return ResponseEntity.ok(productService.updateProduct(productDto, productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> removeProduct(@PathVariable("id") String productId) {
        return ResponseEntity.ok(productService.removeProduct(productId));
    }

}
