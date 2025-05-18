package com.microservice.ePharmaMS.inventory_service.repository;

import com.microservice.ePharmaMS.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
