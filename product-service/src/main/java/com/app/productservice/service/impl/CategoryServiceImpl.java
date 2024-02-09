package com.app.productservice.service.impl;

import com.app.productservice.dto.ApiResponseDto;
import com.app.productservice.dto.CategoryDto;
import com.app.productservice.entity.Category;
import com.app.productservice.exception.GlobalExceptionHandler;
import com.app.productservice.repository.CategoryRepository;
import com.app.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponseDto addCategory(CategoryDto categoryDto) {
        log.info("Inside addCategory method of CategoryServiceImpl");
        if (categoryRepository.findByCategoryName(categoryDto.getCategoryName()).isPresent()) {
            log.info("Category already exists.");
            throw new GlobalExceptionHandler("Category Already Exists");
        } else {
            log.info("Persisting the details into DB");
            categoryDto.setCategoryId(UUID.randomUUID().toString().split("-")[0]);
            categoryRepository.save(modelMapper.map(CategoryDto.builder()
                    .categoryId(categoryDto.getCategoryId())
                    .categoryName(categoryDto.getCategoryName())
                    .categoryDescription(categoryDto.getCategoryDescription())
                    .build(), Category.class));
            log.info("Category saved successfully.");
            return ApiResponseDto.builder()
                    .message("Category Added Successfully")
                    .success(true)
                    .build();
        }
    }

    @Override
    public CategoryDto getCategoryById(String categoryId) {
        log.info("Inside getCategoryById method of CategoryServiceImpl");
        return modelMapper.map(categoryRepository.findById(categoryId).orElseThrow(() -> new
                GlobalExceptionHandler("Category Not Found")), CategoryDto.class);
    }

}