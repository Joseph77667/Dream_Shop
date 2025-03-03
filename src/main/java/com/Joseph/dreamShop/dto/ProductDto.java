package com.Joseph.dreamShop.dto;

import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.model.Image;
import com.Joseph.dreamShop.model.Product;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
