package com.microservice.product_service.service;

import com.microservice.product_service.DTO.ManufacturerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManufacturerService {

    Page<ManufacturerDTO> getAllManufacturers(Pageable page);
    ManufacturerDTO getManufacturerById(Long id) ;
    ManufacturerDTO createManufacturer(ManufacturerDTO dto);
    ManufacturerDTO updateManufacturer(Long id, ManufacturerDTO dto) ;
    void deleteManufacturerByID(Long id) ;
}
