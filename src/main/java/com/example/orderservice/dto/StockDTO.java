package com.example.orderservice.dto;

import com.example.orderservice.entity.Product;

import lombok.Data;

@Data
public class StockDTO {

	private Integer id;
	
	private Product product;
	
	private int amount;
	
	private String statusMessage;
}
