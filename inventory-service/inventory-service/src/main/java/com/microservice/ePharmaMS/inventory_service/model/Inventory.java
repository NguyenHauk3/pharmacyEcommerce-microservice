package com.microservice.ePharmaMS.inventory_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "inventories")
public class Inventory {
    @Id
    private Long productId;

    private int quantity;
}
