package com.example.orderservice.dto;

import java.util.List;

import com.example.orderservice.entity.Product;

import lombok.Data;

@Data
public class OrdersDTO {

	
	private Integer orderId;
	
	private Long totalPrice;
	
    private List<Product> products;
}
