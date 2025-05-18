package com.microservice.product_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String images;

    private String unit;

    private Double price;

    private String description;
    private String huongDanSuDung;
    private String thanhPhan;
    private LocalDate date;

    private LocalDate expiryDate;

    private Long categoryId;

    private Long promotionId;
    private Long manufacturerId;

    private String promotionName;
    private String categoryName;
    private String tenNhaSX;
    private String noiSX;

    private double percent;

}
