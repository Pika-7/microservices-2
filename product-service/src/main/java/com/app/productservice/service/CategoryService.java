package com.app.productservice.service;

import com.app.productservice.dto.ApiResponseDto;
import com.app.productservice.dto.CategoryDto;

public interface CategoryService {
    ApiResponseDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(String categoryId);
}
