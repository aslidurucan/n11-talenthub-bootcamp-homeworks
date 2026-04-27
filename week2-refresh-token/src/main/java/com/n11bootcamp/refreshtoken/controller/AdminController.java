package com.n11bootcamp.refreshtoken.controller;

import com.n11bootcamp.refreshtoken.api.AdminControllerContract;
import com.n11bootcamp.refreshtoken.dto.request.CreateCategoryRequest;
import com.n11bootcamp.refreshtoken.dto.request.CreateProductRequest;
import com.n11bootcamp.refreshtoken.dto.response.CategoryResponse;
import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import com.n11bootcamp.refreshtoken.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController implements AdminControllerContract {

    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<ProductResponse> createProduct(CreateProductRequest request) {
        return ResponseEntity.ok(adminService.createProduct(request));
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long id,
                                                         CreateProductRequest request) {
        return ResponseEntity.ok(adminService.updateProduct(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        adminService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CategoryResponse> createCategory(CreateCategoryRequest request) {
        return ResponseEntity.ok(adminService.createCategory(request));
    }
}