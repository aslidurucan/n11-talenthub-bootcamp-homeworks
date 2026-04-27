package com.n11bootcamp.refreshtoken.controller;

import com.n11bootcamp.refreshtoken.api.ProductControllerContract;
import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import com.n11bootcamp.refreshtoken.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductControllerContract {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> searchProducts(String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }
}