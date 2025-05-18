package com.microservice.product_service.service;

import com.microservice.product_service.DTO.ProductDTO;
import com.microservice.product_service.DTO.ProductWithDiscountDTO;
import com.microservice.product_service.modal.Product;
import com.microservice.product_service.modal.SearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {



    Page<ProductDTO> getAllProducts(Pageable page);
    ProductDTO getProductById(Long id) throws Exception;
    ProductDTO createProduct(ProductDTO dto, MultipartFile file) throws Exception;
    ProductDTO updateProduct(Long id, ProductDTO dto, MultipartFile file) throws Exception;
    void deleteProduct(Long id) throws Exception;
    public Page<ProductWithDiscountDTO> getAllProductsWithDiscount(Pageable page);
    public Page<Product> findAllByCriteria(SearchRequest searchRequest, Pageable pageable);
    List<Product> findByCategoryId(Long categoryId);
    public List<ProductDTO> getProductsWithPromotion();
}
