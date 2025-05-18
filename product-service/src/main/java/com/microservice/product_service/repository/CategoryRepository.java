package com.microservice.product_service.repository;

import com.microservice.product_service.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //List<Product> findByCategory_Id(String categoryId);
    boolean existsByName(String name);
}
