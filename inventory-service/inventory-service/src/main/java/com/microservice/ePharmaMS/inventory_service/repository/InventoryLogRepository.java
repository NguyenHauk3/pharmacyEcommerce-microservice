package com.microservice.ePharmaMS.inventory_service.repository;

import com.microservice.ePharmaMS.inventory_service.model.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog, Long> {
    List<InventoryLog> findByProductId(Long productId);
}
