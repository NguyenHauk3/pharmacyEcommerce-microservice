package com.microservice.product_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerDTO {
    private Long id;

    private String tenNhaSX;
    private String noiSX;
}
