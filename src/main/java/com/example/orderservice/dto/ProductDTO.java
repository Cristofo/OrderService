package com.example.orderservice.dto;

import java.util.List;

import com.example.orderservice.entity.Orders;
import com.example.orderservice.entity.ProductType;

import lombok.Data;

@Data
public class ProductDTO {

	private Integer productId;
	
	private ProductType productType;
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private List<Orders> orders;
}
