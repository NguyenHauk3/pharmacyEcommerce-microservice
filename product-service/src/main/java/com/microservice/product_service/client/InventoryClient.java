//package com.microservice.product_service.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "inventory-service", url = "http://localhost:8080")
//public interface InventoryClient {
//
//    @PostMapping("/api/inventory/import")
//    void importInventory(@RequestParam("productId") Long productId,
//                         @RequestParam("quantity") int quantity);
//}