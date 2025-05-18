package com.microservice.ePharmaMS.inventory_service.service;

import com.microservice.ePharmaMS.inventory_service.DTO.InventoryDTO;
import com.microservice.ePharmaMS.inventory_service.DTO.InventoryForecastDTO;
import com.microservice.ePharmaMS.inventory_service.model.InventoryLog;

import java.util.List;

public interface InventoryService {
    InventoryDTO getInventory(Long productId);
    void importInventory(Long productId, int quantity);
    void exportInventory(Long productId, int quantity);
    List<InventoryLog> getLogs();
    List<InventoryDTO> getLowStock();
    List<InventoryForecastDTO> calculateForecast();
    boolean checkStockAvailability(Long productId, int quantity);
    void deleteInventory(Long productId);
}
