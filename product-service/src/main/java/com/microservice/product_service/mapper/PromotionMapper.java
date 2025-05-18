package com.microservice.product_service.mapper;

import com.microservice.product_service.DTO.PromotionDTO;
import com.microservice.product_service.modal.Promotion;

public class PromotionMapper {
    public static PromotionDTO toDTO(Promotion promotion) {
        PromotionDTO promotionDTO = new PromotionDTO();
        promotionDTO.setId(promotion.getId());
        promotionDTO.setName(promotion.getName());
        promotionDTO.setDate(promotion.getDate());
        promotionDTO.setPercent(promotion.getPercent());

        return promotionDTO;

    }

    public static Promotion toEntity(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        promotion.setId(promotionDTO.getId());
        promotion.setName(promotionDTO.getName());
        promotion.setDate(promotionDTO.getDate());
        promotion.setPercent(promotionDTO.getPercent());


        return promotion;
    }
}
