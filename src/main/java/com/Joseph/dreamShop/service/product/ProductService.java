package com.Joseph.dreamShop.service.product;

import com.Joseph.dreamShop.dto.ImageDto;
import com.Joseph.dreamShop.dto.ProductDto;
import com.Joseph.dreamShop.exception.ProductNotFoundException;
import com.Joseph.dreamShop.exception.ResourceNotFoundException;
import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.model.Image;
import com.Joseph.dreamShop.model.Product;
import com.Joseph.dreamShop.repository.CategoryRepository;
import com.Joseph.dreamShop.repository.ImageRepository;
import com.Joseph.dreamShop.repository.ProductRepository;
import com.Joseph.dreamShop.request.AddProductRequest;
import com.Joseph.dreamShop.request.ProductUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category category1 = new Category(request.getCategory().getName());
                    return categoryRepository.save(category1);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                 () -> {throw new ResourceNotFoundException("Product not found");});
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, long id) {
        return productRepository.findById(id)
                .map(existingProduct -> updateProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private Product updateProduct(Product existingProduct, ProductUpdateRequest request){
                existingProduct.setName(request.getName());
                existingProduct.setBrand(request.getBrand());
                existingProduct.setPrice(request.getPrice());
                existingProduct.setInventory(request.getInventory());
                existingProduct.setDescription(request.getDescription());

                Category category = categoryRepository.findByName(request.getCategory().getName());
                existingProduct.setCategory(category);

                return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(Category category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public int countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }

}
