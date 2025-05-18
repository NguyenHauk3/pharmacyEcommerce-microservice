package com.microservice.ePharmaMS.inventory_service.service.serviceImpl;

import com.microservice.ePharmaMS.inventory_service.DTO.InventoryDTO;
import com.microservice.ePharmaMS.inventory_service.DTO.InventoryForecastDTO;
import com.microservice.ePharmaMS.inventory_service.client.ProductClient;
import com.microservice.ePharmaMS.inventory_service.client.ProductResponse;
import com.microservice.ePharmaMS.inventory_service.model.Inventory;
import com.microservice.ePharmaMS.inventory_service.model.InventoryLog;
import com.microservice.ePharmaMS.inventory_service.repository.InventoryLogRepository;
import com.microservice.ePharmaMS.inventory_service.repository.InventoryRepository;
import com.microservice.ePharmaMS.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryLogRepository inventoryLogRepository;

    @Autowired
    private ProductClient productClient;

    @Override
    public InventoryDTO getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElse(new Inventory(productId, 0));
        return new InventoryDTO(productId, inventory.getQuantity());
    }

    @Override
    public void importInventory(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElse(new Inventory(productId, 0));
        inventory.setQuantity(inventory.getQuantity() + quantity);
        inventoryRepository.save(inventory);

        InventoryLog log = new InventoryLog(null, productId, quantity, "import", LocalDateTime.now());
        inventoryLogRepository.save(log);
    }

    @Override
    public void exportInventory(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Không đủ hàng");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        InventoryLog log = new InventoryLog(null, productId, -quantity, "export", LocalDateTime.now());
        inventoryLogRepository.save(log);
    }

    @Override
    public List<InventoryLog> getLogs() {
        return inventoryLogRepository.findAll();
    }

    @Override
    public List<InventoryDTO> getLowStock() {
        return inventoryRepository.findAll().stream()
                .filter(inv -> inv.getQuantity() < 5)
                .map(inv -> new InventoryDTO(inv.getProductId(), inv.getQuantity()))
                .collect(Collectors.toList());
    }
    @Override
    public boolean checkStockAvailability(Long productId, int quantity) {
        // Logic kiểm tra số lượng trong kho của sản phẩm
        InventoryDTO inventory = getInventory(productId);  // Giả sử bạn đã có phương thức này
        return inventory != null && inventory.getQuantity() >= quantity;
    }

    @Override
    public void deleteInventory(Long productId) {
        inventoryRepository.deleteById(productId);
    }

    @Override
    public List<InventoryForecastDTO> calculateForecast() {
        // Giả sử lấy lịch sử 7 ngày gần nhất
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);

//        //Lọc các log xuất kho trong 7 ngày
        Map<Long, List<InventoryLog>> exportLogsByProduct = inventoryLogRepository.findAll().stream()
                .filter(log -> log.getAction().equalsIgnoreCase("export") && log.getTimestamp().isAfter(weekAgo))
                .collect(Collectors.groupingBy(InventoryLog::getProductId));

        List<InventoryForecastDTO> result = new ArrayList<>();

        //Tính toán số lượng 7 ngày, tính trung bình mỗi này
        for (Map.Entry<Long, List<InventoryLog>> entry : exportLogsByProduct.entrySet()) {
            Long productId = entry.getKey();
            int totalExported = entry.getValue().stream().mapToInt(log -> Math.abs(log.getQuantityChanged())).sum();
            double avgExport = totalExported / 7.0;

            //Dự báo số lượng
            Inventory inv = inventoryRepository.findById(productId).orElse(new Inventory(productId, 0));
            int currentQty = inv.getQuantity();
            int daysLeft = avgExport == 0 ? Integer.MAX_VALUE : (int)(currentQty / avgExport);
            int toRestock = avgExport == 0 ? 0 : (int)Math.ceil(avgExport * 7) - currentQty;
            System.out.println("ProductId: " + productId);
            System.out.println("Total exported: " + totalExported);
            System.out.println("Avg export/day: " + avgExport);
            System.out.println("Current qty: " + currentQty);
            String productName;
            try {
                ProductResponse product = productClient.getProductById(productId);
                productName = product.getName();
            } catch (Exception e) {
                productName = "Không xác định";
            }
            result.add(new InventoryForecastDTO(

                    productId,
                    productName,
                    currentQty,
                    avgExport,
                    daysLeft,
                    Math.max(toRestock, 0)
            ));
        }
        System.out.println("Tổng số log xuất kho trong 7 ngày: " + exportLogsByProduct.size());

        return result;
    }

}