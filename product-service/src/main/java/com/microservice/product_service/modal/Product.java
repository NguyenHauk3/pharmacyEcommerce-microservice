package com.microservice.product_service.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String images;

    private String unit;

    @Column(nullable = false)
    private Double price;

    private String description;


    private String huongDanSuDung;
    private String thanhPhan;

    private LocalDate date;

    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;  

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id")
        private Manufacturer manufacturer;

}
