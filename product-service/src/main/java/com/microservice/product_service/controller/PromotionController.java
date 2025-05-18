package com.microservice.product_service.controller;

import com.microservice.product_service.DTO.PromotionDTO;
import com.microservice.product_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public Page<PromotionDTO> getAll(Pageable pageable) {
        return promotionService.getAllPromotions(pageable);
    }

    @GetMapping("/{id}")
    public PromotionDTO getById(@PathVariable Long id) {
        return promotionService.getPromotionById(id);
    }

    @PostMapping
    public PromotionDTO create(@RequestBody PromotionDTO dto) {
        return promotionService.createPromotion(dto);
    }

    @PutMapping("/{id}")
    public PromotionDTO update(@PathVariable Long id, @RequestBody PromotionDTO dto) {
        return promotionService.updatePromotion(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionService.deletePromotionById(id);
    }
}
