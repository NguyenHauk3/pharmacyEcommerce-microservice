package com.microservice.ePharmaMS.order_service.repository;

import com.microservice.ePharmaMS.order_service.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}