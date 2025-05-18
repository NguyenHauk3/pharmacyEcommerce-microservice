package com.microservice.product_service.mapper;

import com.microservice.product_service.DTO.ProductDTO;
import com.microservice.product_service.modal.Category;
import com.microservice.product_service.modal.Manufacturer;
import com.microservice.product_service.modal.Product;
import com.microservice.product_service.modal.Promotion;


public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
       ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setUnit(product.getUnit());
        dto.setPrice(product.getPrice());
        dto.setImages(product.getImages());
        dto.setDescription(product.getDescription());
        dto.setHuongDanSuDung(product.getHuongDanSuDung());
        dto.setThanhPhan(product.getThanhPhan());
        dto.setDate(product.getDate());
        dto.setExpiryDate(product.getExpiryDate());
        dto.setCategoryId(product.getCategory().getId());
        dto.setCategoryName(product.getCategory().getName());
        dto.setPromotionId(product.getPromotion().getId());
        dto.setPromotionName(product.getPromotion().getName());
        dto.setPercent(product.getPromotion().getPercent());// Gán tên khuyến mãi
        dto.setManufacturerId(product.getManufacturer().getId());
        dto.setTenNhaSX(product.getManufacturer().getTenNhaSX());
        dto.setNoiSX(product.getManufacturer().getNoiSX());
//        dto.setPromotionId(product.getPromotion() != null ? product.getPromotion().getId() : null);
//        dto.setManufacturerId(product.getManufacturer() != null ? product.getManufacturer().getId() : null);


        return dto;
    }

    public static Product toEntity(ProductDTO dto, Category category, Promotion promotion, Manufacturer manufacturer) {
        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setUnit(dto.getUnit());
        p.setPrice(dto.getPrice());
        p.setImages(dto.getImages());
        p.setDate(dto.getDate());
        p.setExpiryDate(dto.getExpiryDate());
        p.setDescription(dto.getDescription());
        p.setHuongDanSuDung(dto.getHuongDanSuDung());
        p.setThanhPhan(dto.getThanhPhan());
        p.setCategory(category);
        p.setPromotion(promotion);
        p.setManufacturer(manufacturer);
        return p;
    }
}
