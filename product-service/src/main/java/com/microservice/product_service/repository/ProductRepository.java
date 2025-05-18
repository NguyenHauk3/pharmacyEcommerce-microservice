package com.microservice.product_service.repository;

import com.microservice.product_service.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    boolean existsByManufacturer_Id(Long manufacturerId);
    List<Product> findByCategory_Id(Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.promotion IS NOT NULL AND p.promotion.percent > 0")
    List<Product> findByPromotionIsNotNull();

    @Query("SELECT s FROM Product s WHERE s.name = :name")
    List<Product> findByProductName(@Param("name") String name);

    @Query("SELECT s FROM Product s WHERE s.name LIKE %:name%")
    List<Product> findByProductNameContainingIgnoreCase(@Param("name") String name);


    @Query("SELECT s FROM Product s WHERE s.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceBetween(@Param("minPrice") Double minPrice,@Param("maxPrice") Double maxPrice);

    @Query("SELECT s FROM Product s WHERE s.price >= :price")
    List<Product> findByPrice(@Param("price") Double price);

    @Query("SELECT s FROM Product s WHERE s.description LIKE %:description%")
    List<Product> findByDescription(@Param("description") String description);

}

