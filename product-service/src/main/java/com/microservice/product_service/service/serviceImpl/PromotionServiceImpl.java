package com.microservice.product_service.service.serviceImpl;


import com.microservice.product_service.DTO.PromotionDTO;
import com.microservice.product_service.mapper.PromotionMapper;
import com.microservice.product_service.modal.Promotion;
import com.microservice.product_service.repository.PromotionRepository;
import com.microservice.product_service.service.PromotionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {


    private final PromotionRepository promotionRepo;

    @Override
    public Page<PromotionDTO> getAllPromotions(Pageable page) {
        return promotionRepo.findAll(page)
                .map(PromotionMapper::toDTO);

    }

    @Override
    public PromotionDTO getPromotionById(Long id) {
        Promotion promotion = promotionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay khuyen mai"));
        return PromotionMapper.toDTO(promotion);
    }

    @Override
    public PromotionDTO createPromotion(PromotionDTO dto) {

        if (promotionRepo.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Khuyến mãi đã tồn tại.");
        }

        Promotion promotion = PromotionMapper.toEntity(dto);
        return PromotionMapper.toDTO(promotionRepo.save(promotion));
    }

    @Override
    public PromotionDTO updatePromotion(Long id, PromotionDTO dto) {
        Promotion p = promotionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay khuyen mai"));

           p.setName(dto.getName());
           p.setDate(dto.getDate());
           p.setPercent(dto.getPercent());

        return PromotionMapper.toDTO(promotionRepo.save(p));
    }

    @Override
    public void deletePromotionById(Long id) {
        promotionRepo.deleteById(id);
    }

}
