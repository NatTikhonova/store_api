package com.store_api.controllers;

import com.store_api.model.Category;
import com.store_api.model.request.NewCategoryRequest;
import com.store_api.model.request.UpdateCategoryRequest;
import com.store_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable Long id
    ) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newCategory(
            @RequestBody NewCategoryRequest categoryRequest
    ) {
        return categoryService.newCategory(categoryRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(
            @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        return categoryService.updateCategory(updateCategoryRequest);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteTicket(
            @PathVariable Long id
    ) {
        return categoryService.deleteCategory(id);
    }
}
