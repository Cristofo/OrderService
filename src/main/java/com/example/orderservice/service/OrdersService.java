package com.example.orderservice.service;

import org.springframework.http.ResponseEntity;

import com.example.orderservice.dto.ProductIdsDTO;

public interface OrdersService {

	public ResponseEntity<Object> registerNewOrder(ProductIdsDTO productIdDto);
	
}
