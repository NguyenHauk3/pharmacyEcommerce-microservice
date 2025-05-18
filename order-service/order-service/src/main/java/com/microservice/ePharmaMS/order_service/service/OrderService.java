package com.microservice.ePharmaMS.order_service.service;

import com.microservice.ePharmaMS.order_service.DTO.OrderDTO;
import com.microservice.ePharmaMS.order_service.modal.Order;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    List<OrderDTO> getOrdersByUserId(Long userId);
    OrderDTO getOrderById(Long id);
    void deleteOrder(Long id);
}