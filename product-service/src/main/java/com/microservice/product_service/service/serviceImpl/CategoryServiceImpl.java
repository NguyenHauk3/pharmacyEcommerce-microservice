package com.microservice.product_service.service.serviceImpl;

import com.microservice.product_service.DTO.CategoryDTO;
import com.microservice.product_service.mapper.CategoryMapper;
import com.microservice.product_service.modal.Category;
import com.microservice.product_service.repository.CategoryRepository;
import com.microservice.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;


    @Override
    public Page<CategoryDTO> getAllCategory(Pageable page) {
        return categoryRepo.findAll(page)
                .map(CategoryMapper::toDTO);

    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return CategoryMapper.toDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {

        if (categoryRepo.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Tên danh mục đã tồn tại.");
        }

        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDTO(categoryRepo.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return CategoryMapper.toDTO(categoryRepo.save(category));
    }

    @Override
    public void deleteCategoryByID(Long id) {
        categoryRepo.deleteById(id);
    }

    //(Xoa san pham khoi danh muc
//    @Override
//    public boolean delProductInCategory(Long idProduct) {
//        Product product = productRepo.findById(idProduct)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//        product.setCategory(null);
//        productRepo.save(product);
//        return true;
//    }

    //Gan san pham vao danh muc
//    @Override
//    public boolean addProductInCategory(Long idCategory, Long idProduct) {
//        Product product = productRepo.findById(idProduct)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//        Category category = categoryRepo.findById(idCategory)
//                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
//        product.setCategory(category);
//        productRepo.save(product);
//        return true;
//    }
}
