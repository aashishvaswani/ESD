package com.aashish.ESD.repo;

import com.aashish.ESD.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE price>= :priceBefore and price<= :priceAfter ORDER BY price DESC LIMIT 2")
    List<Product> findProductsByPriceBetween(Double priceBefore, Double priceAfter);
}
