package com.example.orderservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orderservice.entity.ProductType;

public interface ProductTypeRepo extends JpaRepository<ProductType, Long> {

}
