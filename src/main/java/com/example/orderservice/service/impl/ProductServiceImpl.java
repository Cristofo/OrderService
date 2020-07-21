package com.example.orderservice.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.orderservice.dto.ProductDTO;
import com.example.orderservice.entity.Product;
import com.example.orderservice.entity.Stock;
import com.example.orderservice.repo.ProductRepo;
import com.example.orderservice.repo.StockRepo;
import com.example.orderservice.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StockRepo stockRepo;
	
	@Transactional
	public ProductDTO registerNewProduct(ProductDTO productDto) {
		
		Product product = this.modelMapper.map(productDto, Product.class);
		
		Product productRes = productRepo.save(product);
		
		return this.modelMapper.map(productRes, ProductDTO.class);
		
	}

	@Transactional
	public ResponseEntity<Object> addStockToProduct(Long productId, Integer quantity)throws  NoSuchElementException {
		
		Optional<Product> product = productRepo.findById(productId);
		
		if (product.isPresent()) {
			Stock stockProduct = stockRepo.findByProductId(productId);
		
			Integer totalAmount = null == stockProduct ? quantity : quantity+stockProduct.getAmount();
			if(null == stockProduct) {
				stockProduct = new Stock();
			}
			
			stockProduct.setAmount(totalAmount);
			stockProduct.setProduct(product.get());
			Stock stockRes = stockRepo.save(stockProduct);
			return new ResponseEntity<Object>(stockRes, HttpStatus.OK);
		} else {	
			
			return new ResponseEntity<Object>("Product does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
		
}
