package com.example.cshop.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class ProductUpdateDto {

    private Long id;
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    private String imageUrl;

    @NotNull
    private Long categoryId;

    private Integer stock;

    public ProductUpdateDto() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
