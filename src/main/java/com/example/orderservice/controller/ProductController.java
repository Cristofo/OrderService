package com.example.orderservice.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.dto.ProductDTO;
import com.example.orderservice.entity.Product;
import com.example.orderservice.repo.ProductRepo;
import com.example.orderservice.service.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	private ProductRepo productRepo;
	
	
	@PostMapping("/register")
	public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductDTO productDto){
		
		return new ResponseEntity<>(productService.registerNewProduct(productDto), HttpStatus.OK);
		
	}
	
	@PutMapping("/add-stock/{productId}/{quantity}")
	public ResponseEntity<Object> addStock(@PathVariable("productId") @Min(1) @NotBlank @NotNull Long productId, 
            @PathVariable("quantity") @Min(value = 1, message = "Quantity should be a positive value above zero") Integer quantity){
			return new ResponseEntity<>(productService.addStockToProduct(productId, quantity), HttpStatus.OK);
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProducts(){
		
		return new ResponseEntity<List<Product>>(productRepo.findAll(),HttpStatus.OK);
	}
}
