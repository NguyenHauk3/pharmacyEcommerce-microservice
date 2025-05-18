package com.microservice.product_service.controller;


import com.microservice.product_service.DTO.ProductDTO;
import com.microservice.product_service.DTO.ProductWithDiscountDTO;
import com.microservice.product_service.modal.Product;
import com.microservice.product_service.modal.SearchRequest;
import com.microservice.product_service.repository.ProductRepository;
import com.microservice.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;


    @Value("${app.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload-image/{id}")
    public ResponseEntity<String> uploadImage(@PathVariable Long id,
                                              @RequestParam(value = "images", required = false) MultipartFile imageFile) {
        try {
            // Kiểm tra file rỗng
            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Lỗi: File hình ảnh không được để trống.");
            }

            // Tạo thư mục nếu chưa có
            Files.createDirectories(Paths.get(uploadDir));

            // Tạo tên file ngẫu nhiên
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(uploadDir + File.separator + fileName);

            // Ghi file vào đường dẫn
            Files.write(path, imageFile.getBytes());

            // Tìm sản phẩm theo ID
            Optional<Product> optionalProduct = productRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setImages(fileName); // Lưu tên file
                productRepository.save(product);
                return ResponseEntity.ok("Tải file thành công: " + fileName);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm có ID: " + id);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lưu file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi không xác định: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAll(Pageable page) {
        return ResponseEntity.ok(productService.getAllProducts(page));
    }

    @GetMapping("/with-discount")
    public ResponseEntity<Page<ProductWithDiscountDTO>> getProductsWithDiscount(Pageable page) {
        Page<ProductWithDiscountDTO> products = productService.getAllProductsWithDiscount(page);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "price", required = false) Double price,
                                             @RequestParam(value = "unit", required = false) String unit,
                                             @RequestParam(value = "description", required = false) String description,
                                             @RequestParam(value = "date", required = false) LocalDate date,
                                             @RequestParam(value = "expiryDate", required = false) LocalDate expiryDate,
                                             @RequestParam(value = "thanhPhan", required = false) String thanhPhan,
                                             @RequestParam(value = "huongDanSuDung", required = false) String huongDanSuDung,
                                             @RequestParam(value = "categoryId", required = false) Long categoryId,
                                             @RequestParam(value = "promotionId", required = false) Long promotionId,
                                             @RequestParam(value = "manufacturerId", required = false) Long manufacturerId,
                                             @RequestParam(value = "images", required = false) MultipartFile file
                                             ) throws Exception{

        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setUnit(unit);
        dto.setDescription(description);
        dto.setDate(date);
        dto.setExpiryDate(expiryDate);
        dto.setThanhPhan(thanhPhan);
        dto.setHuongDanSuDung(huongDanSuDung);
        dto.setCategoryId(categoryId);
        dto.setPromotionId(promotionId);
        dto.setManufacturerId(manufacturerId);

        ProductDTO create = productService.createProduct(dto, file);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "price", required = false) Double price,
                                             @RequestParam(value = "unit", required = false) String unit,
                                             @RequestParam(value = "description", required = false) String description,
                                             @RequestParam(value = "date", required = false) LocalDate date,
                                             @RequestParam(value = "expiryDate", required = false) LocalDate expiryDate,
                                             @RequestParam(value = "thanhPhan", required = false) String thanhPhan,
                                             @RequestParam(value = "huongDanSuDung", required = false) String huongDanSuDung,
                                             @RequestParam(value = "categoryId", required = false) Long categoryId,
                                             @RequestParam(value = "promotionId", required = false) Long promotionId,
                                             @RequestParam(value = "manufacturerId", required = false) Long manufacturerId,
                                             @RequestParam(value = "images", required = false) MultipartFile file) throws Exception{

        ProductDTO dto = new ProductDTO();
        dto.setName(name);
        dto.setPrice(price);
        dto.setUnit(unit);
        dto.setDescription(description);
        dto.setDate(date);
        dto.setExpiryDate(expiryDate);
        dto.setThanhPhan(thanhPhan);
        dto.setHuongDanSuDung(huongDanSuDung);
        dto.setCategoryId(categoryId);
        dto.setPromotionId(promotionId);
        dto.setManufacturerId(manufacturerId);

        ProductDTO updated = productService.updateProduct(id, dto, file);
        return ResponseEntity.ok(updated);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public Page<Product> searchProducts(@RequestBody SearchRequest searchRequest) {

        int page = searchRequest.getPage() != null ? searchRequest.getPage() : 0;
        int size = searchRequest.getSize() != null ? searchRequest.getSize() : 9;
        Pageable pageable = PageRequest.of(page, size);
        return productService.findAllByCriteria(searchRequest, pageable);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable Long id) {
        List<Product> products = productService.findByCategoryId(id);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/promotion")
    public ResponseEntity<List<ProductDTO>> getProductsByPromotion() {
        List<ProductDTO> products = productService.getProductsWithPromotion();
        return ResponseEntity.ok(products);
    }

}
