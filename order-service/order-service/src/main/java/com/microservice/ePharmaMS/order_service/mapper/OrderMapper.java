package com.microservice.ePharmaMS.order_service.mapper;

import com.microservice.ePharmaMS.order_service.DTO.OrderDTO;
import com.microservice.ePharmaMS.order_service.DTO.OrderItemDTO;
import com.microservice.ePharmaMS.order_service.modal.Order;
import com.microservice.ePharmaMS.order_service.modal.OrderItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDTO> items = order.getItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setProductId(item.getProductId());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).toList();

        dto.setItems(items);
        return dto;
    }

    public Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setTotalPrice(dto.getTotalPrice());
        order.setStatus(dto.getStatus());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = dto.getItems().stream().map(itemDTO -> {
            OrderItem item = new OrderItem();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setOrder(order); // liên kết ngược
            return item;
        }).toList();

        order.setItems(items);
        return order;
    }
}