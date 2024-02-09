package com.app.productservice.service.impl;

import com.app.productservice.dto.ApiResponseDto;
import com.app.productservice.dto.ProductDto;
import com.app.productservice.entity.Category;
import com.app.productservice.entity.Product;
import com.app.productservice.exception.GlobalExceptionHandler;
import com.app.productservice.repository.CategoryRepository;
import com.app.productservice.repository.ProductRepository;
import com.app.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponseDto addProduct(ProductDto productDto, String categoryId) {
        log.info("Inside addProduct method of ProductServiceImpl");
        if (categoryRepository.existsById(categoryId)) {
            log.info("Persisting the product details to DB");
            if (productRepository.findByProductName(productDto.getProductName()).isPresent()) {
                log.info("Product already exists");
                throw new GlobalExceptionHandler("Product Already Exists.");
            } else {
                productDto.setProductId(UUID.randomUUID().toString().split("-")[0]);
                productDto.setCategoryId(categoryId);
                productRepository.save(modelMapper.map(productDto, Product.class));
                return ApiResponseDto.builder()
                        .message("Product Saved Successfully")
                        .success(true)
                        .build();
            }
        } else {
            log.info("Product already exists");
            throw new GlobalExceptionHandler("Product Already Exists.");
        }
    }

    @Override
    public List<ProductDto> getAllProductsOfCategory(String categoryId) {
        log.info("Inside getAllProductsOfCategory method of ProductServiceImpl");
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new GlobalExceptionHandler("Category Not Found"));
        if (categoryRepository.existsById(categoryId)) {
            List<Product> products = productRepository.findByCategory(category);
            if (products.isEmpty()) {
                throw new GlobalExceptionHandler("No Records Found");
            } else {
                return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
            }
        } else {
            log.info("Category Not Found");
            throw new GlobalExceptionHandler("Category Not Found");
        }
    }

    @Override
    public ApiResponseDto updateProduct(ProductDto productDto, String productId) {
        log.info("Inside updateProduct method of ProductServiceImpl");
        if (productRepository.existsById(productId)) {
            productRepository.save(modelMapper.map(productDto, Product.class));
            return ApiResponseDto.builder()
                    .message("Product Updated Successfully.")
                    .success(true)
                    .build();
        } else {
            log.info("Product Not Found");
            throw new GlobalExceptionHandler("Product Not Found");
        }
    }

    @Override
    public ApiResponseDto removeProduct(String productId) {
        log.info("Inside removeProduct method of ProductServiceImpl");
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return ApiResponseDto.builder()
                    .message("Product Removed Successfully.")
                    .success(true)
                    .build();
        }
        throw new GlobalExceptionHandler("Product Not Found");
    }

    @Override
    public ProductDto getProductById(String productId) {
        log.info("Inside getProductById method of ProductServiceImpl");
        return modelMapper.map(productRepository.findById(productId).orElseThrow(() -> new GlobalExceptionHandler("Product Not Found")), ProductDto.class);
    }

}
