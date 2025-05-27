package com.microservice.ePharmaMS.order_service.service.serviceImpl;

import com.microservice.ePharmaMS.order_service.DTO.*;
import com.microservice.ePharmaMS.order_service.client.InventoryClient;
import com.microservice.ePharmaMS.order_service.client.PaymentClient;
import com.microservice.ePharmaMS.order_service.client.ProductClient;
import com.microservice.ePharmaMS.order_service.client.UserClient;
import com.microservice.ePharmaMS.order_service.exception.ResourceNotFoundException;
import com.microservice.ePharmaMS.order_service.mapper.OrderMapper;
import com.microservice.ePharmaMS.order_service.modal.Order;
import com.microservice.ePharmaMS.order_service.modal.OrderItem;
import com.microservice.ePharmaMS.order_service.repository.OrderRepository;
import com.microservice.ePharmaMS.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return orderMapper.toDTO(updatedOrder);
    }
    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .toList();
    }

    @Override
    public List<Order> getAllOrdersSorted() {
        return orderRepository.findAllOrderByCreatedAtDesc();
    }

    @Override
    public List<OrderReportDTO> getOrderReportLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(6).with(LocalTime.MIN);
        List<OrderReportProjection> projections = orderRepository.getOrderStatsLast7Days(sevenDaysAgo);

        return projections.stream()
                .map(p -> new OrderReportDTO(p.getDate(), p.getOrderCount(), p.getTotalRevenue()))
                .toList();
    }
    @Override
    public List<OrderReportDTO> getOrderReportLast30Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(29).with(LocalTime.MIN);
        List<OrderReportProjection> projections = orderRepository.getOrderStatsLast7Days(sevenDaysAgo);

        return projections.stream()
                .map(p -> new OrderReportDTO(p.getDate(), p.getOrderCount(), p.getTotalRevenue()))
                .toList();
    }

}

