package com.n11bootcamp.refreshtoken.api;

import com.n11bootcamp.refreshtoken.dto.request.CreateCategoryRequest;
import com.n11bootcamp.refreshtoken.dto.request.CreateProductRequest;
import com.n11bootcamp.refreshtoken.dto.response.CategoryResponse;
import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/admin")
public interface AdminControllerContract {

    @PostMapping("/products")
    ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request);

    @PutMapping("/products/{id}")
    ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest request);

    @DeleteMapping("/products/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable Long id);

    @PostMapping("/categories")
    ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CreateCategoryRequest request);
}