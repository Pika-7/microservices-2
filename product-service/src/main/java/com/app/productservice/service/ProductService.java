package com.app.productservice.service;

import com.app.productservice.dto.ApiResponseDto;
import com.app.productservice.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ApiResponseDto addProduct(ProductDto productDto, String categoryId);

    List<ProductDto> getAllProductsOfCategory(String categoryId);

    ProductDto getProductById(String productId);

    ApiResponseDto updateProduct(ProductDto productDto, String productId);

    ApiResponseDto removeProduct(String productId);
}
