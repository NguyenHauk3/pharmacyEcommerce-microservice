package com.microservice.product_service.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "tenNhaSX")
    private String tenNhaSX;
    private String noiSX;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
