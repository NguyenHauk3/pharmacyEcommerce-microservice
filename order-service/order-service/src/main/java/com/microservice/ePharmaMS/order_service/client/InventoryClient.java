package com.microservice.ePharmaMS.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "http://localhost:8080")
public interface InventoryClient {
    @GetMapping("/api/inventory/check")
    boolean checkStock(@RequestParam Long productId, @RequestParam int quantity);
}