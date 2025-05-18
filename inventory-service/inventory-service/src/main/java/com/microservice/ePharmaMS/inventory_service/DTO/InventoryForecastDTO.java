package com.microservice.ePharmaMS.inventory_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForecastDTO {
    private Long productId;
    private String productName;
    private int currentQuantity;
    private double avgExportPerDay;
    private int daysLeft; // còn đủ trong bao nhiêu ngày
    private int quantityToRestock;
}
