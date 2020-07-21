package com.example.orderservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orderservice.entity.Stock;

public interface StockRepo extends JpaRepository<Stock, Long>{


	Stock findByProductId(Long productId);

}
