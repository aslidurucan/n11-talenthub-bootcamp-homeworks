package com.n11bootcamp.refreshtoken.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateCategoryRequest {

    @NotBlank(message = "Kategori adı boş olamaz")
    private String name;

    public CreateCategoryRequest() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}