package com.microservice.ePharmaMS.inventory_service.controller;

import com.microservice.ePharmaMS.inventory_service.DTO.InventoryDTO;
import com.microservice.ePharmaMS.inventory_service.DTO.InventoryForecastDTO;
import com.microservice.ePharmaMS.inventory_service.model.InventoryLog;
import com.microservice.ePharmaMS.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventory(productId));
    }

    @PostMapping("/import")
    public ResponseEntity<String> importInventory(@RequestParam Long productId, @RequestParam int quantity) {
        inventoryService.importInventory(productId, quantity);
        return ResponseEntity.ok("Import successful");
    }

    @PostMapping("/export")
    public ResponseEntity<String> exportInventory(@RequestParam Long productId, @RequestParam int quantity) {
        inventoryService.exportInventory(productId, quantity);
        return ResponseEntity.ok("Export successful");
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryDTO>> getLowStock() {
        return ResponseEntity.ok(inventoryService.getLowStock());
    }

    @GetMapping("/logs")
    public ResponseEntity<List<InventoryLog>> getLogs() {
        return ResponseEntity.ok(inventoryService.getLogs());
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkStock(@RequestParam Long productId, @RequestParam int quantity) {
        boolean isInStock = inventoryService.checkStockAvailability(productId, quantity);
        return ResponseEntity.ok(isInStock);
    }

    @GetMapping("/forecast")
    public ResponseEntity<List<InventoryForecastDTO>> getForecast() {
        return ResponseEntity.ok(inventoryService.calculateForecast());
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteInventoryByProductId(@PathVariable Long productId) {
        inventoryService.deleteInventory(productId);
        return ResponseEntity.ok("Xóa tồn kho thành công");
    }
}
