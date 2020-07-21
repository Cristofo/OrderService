package com.example.orderservice.service;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;

import com.example.orderservice.dto.ProductDTO;


public interface ProductService {

	public ProductDTO registerNewProduct(ProductDTO productDto);
		
	public ResponseEntity<Object> addStockToProduct(Long productId, Integer quantity) throws NoSuchElementException;
}
