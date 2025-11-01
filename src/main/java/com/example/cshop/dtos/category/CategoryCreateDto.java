package com.example.cshop.dtos.category;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreateDto {

    @NotBlank
    private String name;

    private String description;

    public CategoryCreateDto() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

