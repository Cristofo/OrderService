package com.example.orderservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.orderservice.dto.OrdersDTO;
import com.example.orderservice.dto.ProductIdsDTO;
import com.example.orderservice.dto.ShippingDTO;
import com.example.orderservice.entity.Orders;
import com.example.orderservice.entity.Product;
import com.example.orderservice.entity.Stock;
import com.example.orderservice.repo.OrdersRepo;
import com.example.orderservice.repo.ProductRepo;
import com.example.orderservice.repo.StockRepo;
import com.example.orderservice.service.OrdersService;

import reactor.core.publisher.Mono;

@Service
public class OrdersServerImpl implements OrdersService{

	@Autowired
	private StockRepo stockRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private OrdersRepo ordersRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private WebClient webClient;
	
	public ResponseEntity<Object> registerNewOrder(ProductIdsDTO productsIdDto){
		
		 Set<Long> productsIds = productsIdDto.getProductIdList();
		 
		 if (null == productsIds || productsIds.isEmpty()) {
			 return new ResponseEntity<>("Empty product id list", HttpStatus.BAD_REQUEST);
		 } else {
			 
			 //agroup ids
			 Map<Long, Long> productsMap = productsIds.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			 
			 Long sumaTotal=0L;
			 List<Product> productList = new ArrayList<>();
			 //validate that products exist and sum
			  for (Map.Entry<Long,Long> entry : productsMap.entrySet())  {
		           if( !productRepo.existsById(entry.getKey().longValue())) {
		        	   return new ResponseEntity<>("One or more product ids do not exist", HttpStatus.BAD_REQUEST);
		           }
		           sumaTotal += sumarMonto(entry.getKey().longValue(), entry.getValue().longValue());
		           
		           Optional<Product> product= productRepo.findById(entry.getKey().longValue());
		           productList.add(product.get());
		            
			  }
			  
			  //supply stock addition 5
			  for (Map.Entry<Long,Long> entry : productsMap.entrySet())  {
		           
				  supplyStock(entry.getKey(),entry.getValue());
		            
			  }
			  
			  //call Shipping service
			  ShippingDTO shippingDto = callShippingService(productsIds);
			  Boolean resShipping =  (shippingDto != null && shippingDto.getHashCode()>0) ? true	: false;


			  if(resShipping) {
				//save order
				  OrdersDTO ordersDto = new OrdersDTO();
				  ordersDto.setTotalPrice(sumaTotal);
				  ordersDto.setProducts(productList);
				  Orders orders = this.modelMapper.map(ordersDto, Orders.class);
				  ordersRepo.save(orders);
				  
				  //update Stock
				  updateStock(productsMap);
				  
				  return new ResponseEntity<Object>(orders, HttpStatus.OK);
			  } else {
				  return new ResponseEntity<Object>("Correct shipping for order", HttpStatus.OK);
			  }
			  
		 } 
			 
	}
	
	private Long sumarMonto(Long productId, Long amount) {
		Optional<Product> product = productRepo.findById(productId.longValue());
		
		if(null == product.get().getPrice()) {
			return 0L;
		} else {
			return amount*product.get().getPrice().longValue();
		}
		
	}
	
	
	private void supplyStock(Long productId,  Long stockNeeded) {
		
		
		//valido si tiene stock
		Stock stockProduct = stockRepo.findByProductId(productId.longValue());
		
		if(null == stockProduct || stockNeeded > stockProduct.getAmount()) {
			//añade 5 al supply
			Long supply =5L;
					
			
			
			Long totalAmount = null == stockProduct ? supply : supply+stockProduct.getAmount();
			if(null == stockProduct) {
				stockProduct = new Stock();
			}
			if(stockProduct.getAmount()== null || stockProduct.getAmount() < stockNeeded) {
				Optional<Product> product = productRepo.findById(productId.longValue());
				stockProduct.setAmount(totalAmount.intValue());
				stockProduct.setProduct(product.get());
				stockRepo.save(stockProduct);
			}
			
			Long difference = stockNeeded-stockProduct.getAmount();
			if(difference > 0) {
				supplyStock(productId, stockNeeded);
			}
		}
	}
	
	
	private ShippingDTO callShippingService( Set<Long> products) {
		
		ShippingDTO shippingDto = new ShippingDTO();
		shippingDto.setListIds(products);
		ShippingDTO shippingServiceResult = webClient.post().uri("/shipping/ship")
				.body(Mono.just(shippingDto), ShippingDTO.class).retrieve().bodyToMono(ShippingDTO.class)
				.log().block();
		
		return shippingServiceResult;
	}
	
	
	private void updateStock(Map<Long, Long> productsMap) {
		
		
		productsMap.forEach((k,v)-> {
		
			Optional<Product> product = productRepo.findById(k.longValue());
				Stock stockProduct = stockRepo.findByProductId(product.get().getId());
			
				Long totalAmount = null == stockProduct ? -v : stockProduct.getAmount().longValue()-v;
				if(null == stockProduct) {
					stockProduct = new Stock();
				}
				
				stockProduct.setAmount(totalAmount.intValue());
				stockProduct.setProduct(product.get());
				stockRepo.save(stockProduct);
		});
	}
	
}

