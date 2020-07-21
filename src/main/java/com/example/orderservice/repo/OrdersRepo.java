package com.example.orderservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orderservice.entity.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Long>{

}
