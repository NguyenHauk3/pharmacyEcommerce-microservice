package com.microservice.product_service.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private Long id;
    private String name;
    private LocalDateTime date;
    private double percent;
}
