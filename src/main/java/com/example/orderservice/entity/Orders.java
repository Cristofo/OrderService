package com.example.orderservice.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="ORDERS")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="TOTAL_PRICE", nullable = false)
	private String totalPrice;
	
	@ManyToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private Set<Product> products;
	
}
