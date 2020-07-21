package com.example.orderservice.dto;

import java.util.Set;

import lombok.Data;

@Data
public class ShippingDTO {
	
	private String tag;
	private Long hashCode;
	private Set<Long> listIds;
	
}
