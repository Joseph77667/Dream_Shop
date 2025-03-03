package com.Joseph.dreamShop.service.product;

import com.Joseph.dreamShop.dto.ProductDto;
import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.model.Product;
import com.Joseph.dreamShop.request.AddProductRequest;
import com.Joseph.dreamShop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    void deleteProductById(long id);
    Product getProductById(long id);
    Product updateProduct(ProductUpdateRequest product, long id);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
