package com.microservice.product_service.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProductWithDiscountDTO {
    private Long id;
    private String name;
    private String images;
    private String unit;
    private Double originalPrice;
    private Double discountedPrice;
    private String description;
    private String huongDanSuDung;
    private String thanhPhan;

}
