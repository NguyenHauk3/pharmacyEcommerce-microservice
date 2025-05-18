package com.microservice.ePharmaMS.order_service.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
