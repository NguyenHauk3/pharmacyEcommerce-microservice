package com.microservice.product_service.repository;

import com.microservice.product_service.modal.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    boolean existsByTenNhaSX(String tenNhaSX);

}
