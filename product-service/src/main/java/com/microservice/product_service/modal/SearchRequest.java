package com.microservice.product_service.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private String unit;
    private String description;
    private Integer page;
    private Integer size;
    private String sort;
}
