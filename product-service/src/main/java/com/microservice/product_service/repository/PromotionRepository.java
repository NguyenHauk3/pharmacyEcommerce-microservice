package com.microservice.product_service.repository;

import com.microservice.product_service.modal.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    boolean existsByName(String name);
}
