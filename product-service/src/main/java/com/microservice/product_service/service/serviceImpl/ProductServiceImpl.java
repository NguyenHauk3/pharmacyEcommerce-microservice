package com.microservice.product_service.service.serviceImpl;

import com.microservice.product_service.DTO.ProductDTO;
import com.microservice.product_service.DTO.ProductWithDiscountDTO;
import com.microservice.product_service.exception.ProductException;
import com.microservice.product_service.mapper.ProductMapper;
import com.microservice.product_service.modal.*;
import com.microservice.product_service.repository.CategoryRepository;
import com.microservice.product_service.repository.ManufacturerRepository;
import com.microservice.product_service.repository.ProductRepository;
import com.microservice.product_service.repository.PromotionRepository;
import com.microservice.product_service.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private PromotionRepository promotionRepo;
    @Autowired
    private ManufacturerRepository manufacturerRepo;

    @Autowired
    EntityManager entityManager;

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public Page<ProductDTO> getAllProducts(Pageable page) {
        return productRepo.findAll(page)
                .map(ProductMapper::toDTO);

    }
    @Override
    public Page<ProductWithDiscountDTO> getAllProductsWithDiscount(Pageable page) {
        Page<Product> products = productRepo.findAll(page);

        // Map Product sang ProductWithDiscountDTO
        List<ProductWithDiscountDTO> dtoList = products.stream().map(product -> {
            double originalPrice = product.getPrice();
            Promotion promo = product.getPromotion();
            double discountedPrice = originalPrice;
            if (promo != null) {
                discountedPrice = originalPrice * (1 - promo.getPercent() / 100);
            }

            return new ProductWithDiscountDTO(
                    product.getId(),
                    product.getName(),
                    product.getImages(),
                    product.getUnit(),
                    originalPrice,
                    discountedPrice,
                    product.getDescription(),
                    product.getHuongDanSuDung(),
                    product.getThanhPhan()
            );
        }).toList();

        // Trả về một Page mới với DTO, giữ nguyên thông tin phân trang của Page gốc
        return new PageImpl<>(dtoList, page, products.getTotalElements());
    }

    @Override
    public ProductDTO getProductById(Long id) throws ProductException {
        Optional<Product> product = productRepo.findById(id);
        if(product.isPresent()){
            return ProductMapper.toDTO(product.get());
        }
        throw new ProductException("Product not found");


    }

    @Override
    public ProductDTO createProduct(ProductDTO dto, MultipartFile file) throws ProductException {
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ProductException("Category not found"));
        Manufacturer manufacturer = manufacturerRepo.findById(dto.getManufacturerId())
                .orElseThrow(() -> new ProductException("Manufacturer not found"));
        Promotion promotion = promotionRepo.findById(dto.getPromotionId())
                .orElseThrow(() -> new ProductException("Promotion not found"));
        if (productRepo.existsByName(dto.getName()) && productRepo.existsByManufacturer_Id(dto.getManufacturerId())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại.");
        }

        Product product = ProductMapper.toEntity(dto,category, promotion, manufacturer);


        if (file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            product.setImages(fileName);
        } else {
            product.setImages(product.getImages());
        }
        return ProductMapper.toDTO(productRepo.save(product));
    }



    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto, MultipartFile file) throws Exception{
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new ProductException("Product not found"));
        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ProductException("Category not found"));
        Promotion promotion = promotionRepo.findById(dto.getPromotionId())
                .orElseThrow(() -> new ProductException("Promotion not found"));
        Manufacturer manufacturer = manufacturerRepo.findById(dto.getManufacturerId())
                .orElseThrow(() -> new ProductException("Manufacturer not found"));

        existing.setName(dto.getName());
        existing.setUnit(dto.getUnit());
        existing.setPrice(dto.getPrice());
        existing.setDescription(dto.getDescription());
        existing.setHuongDanSuDung(dto.getHuongDanSuDung());
        existing.setThanhPhan(dto.getThanhPhan());
        existing.setDate(dto.getDate());
        existing.setExpiryDate(dto.getExpiryDate());
        existing.setCategory(category);
        existing.setPromotion(promotion);
        existing.setManufacturer(manufacturer);


        if (file != null && !file.isEmpty()) {
            String fileName = saveFile(file);
            existing.setImages(fileName);
        } else {
            existing.setImages(existing.getImages());
        }



        return ProductMapper.toDTO(productRepo.save(existing));
    }

    private String saveFile(MultipartFile file) {
        if (file.isEmpty())
            throw new RuntimeException("Failed to store empty file");

        try {
            java.nio.file.Path directory = Paths.get(uploadDir);
            if (!Files.exists(directory))
                Files.createDirectories(directory);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            java.nio.file.Path destPath = directory.resolve(fileName);
            file.transferTo(destPath);

            return fileName;
        } catch (Exception e) {
            // TODO: handle exception
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void deleteProduct(Long id) throws ProductException {
        if (!productRepo.existsById(id)) {
            throw new ProductException("Product not found");
        }
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepo.findByCategory_Id(categoryId);
    }

    @Override
    public List<ProductDTO> getProductsWithPromotion() {
        List<Product> products = productRepo.findByPromotionIsNotNull();
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<Product> findAllByCriteria(SearchRequest searchRequest, Pageable pageable) {
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT s FROM Product s WHERE 1 = 1 ");


        if (searchRequest.getName() != null && !searchRequest.getName().isBlank()) {
            jpql.append("AND s.name LIKE :name ");
        }

        if (searchRequest.getUnit() != null && !searchRequest.getUnit().isBlank()) {
            jpql.append("AND s.unit LIKE :unit ");
        }


        if (searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank() ) {

            jpql.append("AND s.description LIKE :description ");

        }
        if(searchRequest.getMinPrice() != null && searchRequest.getMinPrice() !=0) {

            jpql.append("AND s.price >= :minPrice ");
        }
        if(searchRequest.getMaxPrice() != null && searchRequest.getMaxPrice() !=0 ) {

            jpql.append("AND s.price <= :maxPrice ");
        }



        jpql.append("ORDER BY s.id ASC");

        TypedQuery<Product> query = entityManager.createQuery(jpql.toString().trim(), Product.class);

        if (searchRequest.getName() != null && !searchRequest.getName().isBlank())
            query.setParameter("name", "%" + searchRequest.getName() + "%");

        if (searchRequest.getUnit() != null && !searchRequest.getUnit().isBlank())
            query.setParameter("unit", "%" + searchRequest.getUnit() + "%");

        if (searchRequest.getDescription() != null && !searchRequest.getDescription().isBlank() )
            query.setParameter("description", "%" + searchRequest.getDescription() + "%");

        if (searchRequest.getMinPrice() != null && searchRequest.getMinPrice() != 0   )
            query.setParameter("minPrice",searchRequest.getMinPrice());

        if (searchRequest.getMaxPrice() != null && searchRequest.getMaxPrice() != 0  )
            query.setParameter("maxPrice",searchRequest.getMaxPrice());


        int totalElements = query.getResultList().size();

        query.setFirstResult((int)pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Product> li = query.getResultList();

        return new PageImpl<>(li, pageable, totalElements);
    }
}
