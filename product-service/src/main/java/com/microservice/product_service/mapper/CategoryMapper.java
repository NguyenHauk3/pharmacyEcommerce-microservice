package com.microservice.product_service.mapper;


import com.microservice.product_service.DTO.CategoryDTO;
import com.microservice.product_service.modal.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        return categoryDTO;

    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

         return category;   
    }
}
