package com.n11bootcamp.refreshtoken.service.impl;

import com.n11bootcamp.refreshtoken.converter.ProductConverter;
import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import com.n11bootcamp.refreshtoken.exception.EntityNotFoundException;
import com.n11bootcamp.refreshtoken.repository.ProductRepository;
import com.n11bootcamp.refreshtoken.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productConverter.toResponseList(productRepository.findAll());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productConverter.toResponse(
                productRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı: " + id))
        );
    }

    @Override
    public List<ProductResponse> searchProducts(String name) {
        return productConverter.toResponseList(
                productRepository.findByNameContainingIgnoreCase(name)
        );
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productConverter.toResponseList(
                productRepository.findByCategoryId(categoryId)
        );
    }
}