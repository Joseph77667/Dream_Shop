package com.Joseph.dreamShop.controller;

import com.Joseph.dreamShop.exception.AlreadyExistsException;
import com.Joseph.dreamShop.exception.ResourceNotFoundException;
import com.Joseph.dreamShop.model.Category;
import com.Joseph.dreamShop.reponse.ApiResponse;
import com.Joseph.dreamShop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.swing.text.html.HTML.Tag.I;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try{
            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Added", theCategory));
        }catch (AlreadyExistsException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable long id){
        try{
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found", theCategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable long id){
        try{
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable long id, @RequestParam Category name){
        try{
            Category updatedCategory = categoryService.updateCategory(name, id);
            return ResponseEntity.ok(new ApiResponse("Updated", updatedCategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
