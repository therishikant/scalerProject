package com.example.ScalerProject.repositories;

import com.example.ScalerProject.models.Category;
import com.example.ScalerProject.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);

    @Override
    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByCategory(Category category);

    List<Product> findByCategory_Name(String categoryName);

    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query(value = "select * from product where category_id in (select id from category where name = :categoryName)", nativeQuery = true)
    List<Product> findByCategoryNameNative(@Param("categoryName") String categoryName);

    
}
