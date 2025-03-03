package com.Joseph.dreamShop.service.category;

import com.Joseph.dreamShop.exception.AlreadyExistsException;
import com.Joseph.dreamShop.exception.ResourceNotFoundException;
import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return  Optional.of(category).filter(c -> !categoryRepository.existsByName(category.getName()))
                .map(categoryRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(category.getName()+" already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }) .orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
    }


    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });

    }
}
