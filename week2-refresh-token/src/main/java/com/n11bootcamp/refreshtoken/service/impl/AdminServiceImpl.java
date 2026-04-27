package com.n11bootcamp.refreshtoken.service.impl;

import com.n11bootcamp.refreshtoken.converter.CategoryConverter;
import com.n11bootcamp.refreshtoken.converter.ProductConverter;
import com.n11bootcamp.refreshtoken.dto.request.CreateCategoryRequest;
import com.n11bootcamp.refreshtoken.dto.request.CreateProductRequest;
import com.n11bootcamp.refreshtoken.dto.response.CategoryResponse;
import com.n11bootcamp.refreshtoken.dto.response.ProductResponse;
import com.n11bootcamp.refreshtoken.model.Category;
import com.n11bootcamp.refreshtoken.model.Product;
import com.n11bootcamp.refreshtoken.repository.CategoryRepository;
import com.n11bootcamp.refreshtoken.repository.ProductRepository;
import com.n11bootcamp.refreshtoken.exception.BusinessException;
import com.n11bootcamp.refreshtoken.exception.EntityNotFoundException;
import com.n11bootcamp.refreshtoken.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Kategori bulunamadı"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return productConverter.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Kategori bulunamadı"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return productConverter.toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));
        productRepository.delete(product);
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        if (categoryRepository.findByName(request.getName()).isPresent()) {
            throw new BusinessException("Bu kategori zaten mevcut");
        }

        Category category = new Category(request.getName());
        return categoryConverter.toResponse(categoryRepository.save(category));
    }
}