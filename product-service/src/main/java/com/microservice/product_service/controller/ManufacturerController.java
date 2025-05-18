package com.microservice.product_service.controller;

import com.microservice.product_service.DTO.ManufacturerDTO;
import com.microservice.product_service.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {
    @Autowired
    private ManufacturerService manufacturerService;


    @GetMapping
    public Page<ManufacturerDTO> getAllManufacturers(Pageable pageable) {
        return manufacturerService.getAllManufacturers(pageable);
    }

    @GetMapping("/{id}")
    public ManufacturerDTO getManufacturerById(@PathVariable Long id) {
        return manufacturerService.getManufacturerById(id);
    }

    @PostMapping
    public ManufacturerDTO createManufacturer(@RequestBody ManufacturerDTO dto) {
        return manufacturerService.createManufacturer(dto);
    }

    @PutMapping("/{id}")
    public ManufacturerDTO updateManufacturer(@PathVariable Long id, @RequestBody ManufacturerDTO dto) {
        return manufacturerService.updateManufacturer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        manufacturerService.deleteManufacturerByID(id);
    }
}
