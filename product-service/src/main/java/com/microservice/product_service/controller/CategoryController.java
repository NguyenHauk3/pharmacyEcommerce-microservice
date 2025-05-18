package com.microservice.product_service.controller;

import com.microservice.product_service.DTO.CategoryDTO;
import com.microservice.product_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategory(pageable);
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO dto) {
        return categoryService.createCategory(dto);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryByID(id);
    }

//    @PutMapping("/{categoryId}/add-product/{productId}")
//    public boolean addProductToCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
//        return categoryService.addProductInCategory(categoryId, productId);
//    }
//
//    @PutMapping("/remove-product/{productId}")
//    public boolean removeProductFromCategory(@PathVariable Long productId) {
//        return categoryService.delProductInCategory(productId);
//    }
}