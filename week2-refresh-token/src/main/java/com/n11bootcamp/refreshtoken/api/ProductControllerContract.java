package com.n11bootcamp.refreshtoken.api;

import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductControllerContract {

    @GetMapping
    ResponseEntity<List<ProductResponse>> getAllProducts();

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name);

    @GetMapping("/category/{categoryId}")
    ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId);
}