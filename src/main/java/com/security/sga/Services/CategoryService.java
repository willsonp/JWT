package com.security.sga.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.security.sga.Models.Category;
import com.security.sga.Repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setDescripcion(updatedCategory.getDescripcion());
            return categoryRepository.save(category);
        }).orElse(null);
    }
}
