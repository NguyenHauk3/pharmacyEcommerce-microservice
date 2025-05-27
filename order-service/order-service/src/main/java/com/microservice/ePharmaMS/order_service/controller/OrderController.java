package com.microservice.ePharmaMS.order_service.controller;

import com.microservice.ePharmaMS.order_service.DTO.OrderDTO;
import com.microservice.ePharmaMS.order_service.DTO.OrderReportDTO;
import com.microservice.ePharmaMS.order_service.modal.Order;
import com.microservice.ePharmaMS.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrdersSorted();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/report/last-7-days")
    public ResponseEntity<List<OrderReportDTO>> getOrderReportLast7Days() {
        return ResponseEntity.ok(orderService.getOrderReportLast7Days());
    }
    @GetMapping("/report/last-30-days")
    public ResponseEntity<List<OrderReportDTO>> getOrderReportLast30Days() {
        return ResponseEntity.ok(orderService.getOrderReportLast30Days());
    }
}