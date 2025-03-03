package com.Joseph.dreamShop.request;

import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.model.Image;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
