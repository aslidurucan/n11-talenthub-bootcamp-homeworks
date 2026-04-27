package com.n11bootcamp.refreshtoken.service;

import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    List<ProductResponse> searchProducts(String name);

    List<ProductResponse> getProductsByCategory(Long categoryId);
}