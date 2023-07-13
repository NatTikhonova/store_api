package com.store_api.service;

import com.store_api.model.Category;
import com.store_api.model.request.NewCategoryRequest;
import com.store_api.model.request.UpdateCategoryRequest;
import com.store_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> newCategory(NewCategoryRequest categoryRequest) {
        Category category = Category.builder().name(categoryRequest.getName()).build();
        return isSaveCategory(category)
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>("Ошибка сохранения категории", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        Category category = getById(updateCategoryRequest.getId());
        if (category != null) {
            category.setName(updateCategoryRequest.getName());
            return isSaveCategory(category)
                    ? new ResponseEntity<>(category, HttpStatus.OK)
                    : new ResponseEntity<>("Ошибка сохранения категории", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Категория не найдена", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteCategory(Long id){
        Category category = getById(id);
        if (category != null) {
            return isDeleteCategory(category)
                    ? new ResponseEntity<>("Категория удалена", HttpStatus.OK)
                    : new ResponseEntity<>("Ошибка удаления категории", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Категория не найдена", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> getCategoryById(Long id) {
        Category category = getById(id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Категория не найдена", HttpStatus.NOT_FOUND);
        }
    }

    private Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    private boolean isSaveCategory(Category category) {
        try {
            categoryRepository.save(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDeleteCategory(Category category) {
        try {
            categoryRepository.delete(category);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
