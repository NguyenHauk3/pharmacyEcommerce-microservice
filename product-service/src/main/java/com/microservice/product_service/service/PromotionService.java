package com.microservice.product_service.service;

import com.microservice.product_service.DTO.PromotionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {
    Page<PromotionDTO> getAllPromotions(Pageable page);
    PromotionDTO getPromotionById(Long id);
    PromotionDTO createPromotion(PromotionDTO dto);
    PromotionDTO updatePromotion(Long id, PromotionDTO dto);
    void deletePromotionById(Long id);
}
