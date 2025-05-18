package com.microservice.ePharmaMS.inventory_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long productId;
    private int quantity;
}
