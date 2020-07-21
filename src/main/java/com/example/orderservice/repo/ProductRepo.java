package com.example.orderservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orderservice.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
