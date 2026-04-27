package com.n11bootcamp.refreshtoken.service;

import com.n11bootcamp.refreshtoken.dto.request.CreateCategoryRequest;
import com.n11bootcamp.refreshtoken.dto.request.CreateProductRequest;
import com.n11bootcamp.refreshtoken.dto.response.CategoryResponse;
import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;

public interface AdminService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Long id, CreateProductRequest request);

    void deleteProduct(Long id);

    CategoryResponse createCategory(CreateCategoryRequest request);
}