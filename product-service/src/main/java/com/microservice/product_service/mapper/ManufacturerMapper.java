package com.microservice.product_service.mapper;

import com.microservice.product_service.DTO.ManufacturerDTO;
import com.microservice.product_service.modal.Manufacturer;

public class ManufacturerMapper {

    public static ManufacturerDTO toDTO(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setId(manufacturer.getId());
        manufacturerDTO.setTenNhaSX(manufacturer.getTenNhaSX());
        manufacturerDTO.setNoiSX(manufacturer.getNoiSX());
        return manufacturerDTO;

    }

    public static Manufacturer toEntity(ManufacturerDTO manufacturerDTO) {
       Manufacturer manufacturer = new Manufacturer();
       manufacturer.setId(manufacturerDTO.getId());
       manufacturer.setNoiSX(manufacturerDTO.getNoiSX());
       manufacturer.setTenNhaSX(manufacturerDTO.getTenNhaSX());

        return manufacturer;
    }
}
