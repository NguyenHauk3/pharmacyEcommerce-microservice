package com.microservice.ePharmaMS.order_service.repository;

import com.microservice.ePharmaMS.order_service.DTO.OrderReportDTO;
import com.microservice.ePharmaMS.order_service.DTO.OrderReportProjection;
import com.microservice.ePharmaMS.order_service.modal.Order;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    @Query("SELECT o FROM Order o ORDER BY o.createdAt DESC")
    List<Order> findAllOrderByCreatedAtDesc();


    @Query("SELECT DATE(o.createdAt) AS date, COUNT(o.id) AS orderCount, SUM(o.totalPrice) AS totalRevenue " +
            "FROM Order o WHERE o.createdAt >= :startDate " +
            "GROUP BY DATE(o.createdAt) ORDER BY DATE(o.createdAt)")
    List<OrderReportProjection> getOrderStatsLast7Days(@Param("startDate") LocalDateTime startDate);
}