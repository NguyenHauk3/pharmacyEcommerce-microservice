package com.microservice.ePharmaMS.order_service.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}
