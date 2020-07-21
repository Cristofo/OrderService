package com.example.orderservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orderservice.dto.ProductIdsDTO;
import com.example.orderservice.entity.Orders;
import com.example.orderservice.repo.OrdersRepo;
import com.example.orderservice.service.OrdersService;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	OrdersService orderService;
	
	@Autowired
	private OrdersRepo orderRepo;
	
	@PostMapping("/create")
	public ResponseEntity<Object> addOrder(@Valid @RequestBody ProductIdsDTO productIdDto){
		
		return new ResponseEntity<>(orderService.registerNewOrder(productIdDto), HttpStatus.OK);
			
	}
	
	
	@GetMapping("/")
	public ResponseEntity<List<Orders>> getAllOrders(){
		return new ResponseEntity<List<Orders>>(orderRepo.findAll(),HttpStatus.OK);
	}
}
