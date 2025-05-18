package com.microservice.product_service.service;

import com.microservice.product_service.DTO.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

//    public boolean delProductInCategory(Long idProduct);
//    public boolean addProductInCategory(Long idCategory, Long idProduct);


    Page<CategoryDTO> getAllCategory(Pageable page);
    CategoryDTO getCategoryById(Long id) ;
    CategoryDTO createCategory(CategoryDTO dto);
    CategoryDTO updateCategory(Long id, CategoryDTO dto) ;
    void deleteCategoryByID(Long id) ;

}
