package com.example.orderservice.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "FK_PRODUCT_TYPE")
	private ProductType productType;
	private String name;
	private String description;
	private Double price;
	
	
	@JoinTable(
	        name = "PRODUCT_ORDERS",
	        joinColumns = @JoinColumn(name = "FK_PRODUCT_ID", nullable = false),
	        inverseJoinColumns = @JoinColumn(name="FK_ORDER_ID", nullable = false)
	    )
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Orders> orders;
	
	@OneToOne(mappedBy = "product")
	private Stock stock;
	

	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        Product other = (Product) obj;       
	        if (id == null) {
	            if (other.id != null)
	                return false;
	        } else if (!id.equals(other.id))
	            return false;        
	        if (productType == null) {
	            if (other.productType != null)
	                return false;
	        } else if (!productType.equals(other.productType))
	            return false;
	        return true;
	    }

	 


	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((description == null) ? 0 : description.hashCode());
	        result = prime * result + ((id == null) ? 0 : id.hashCode());
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        result = prime * result + ((price == null) ? 0 : price.hashCode());
	        result = prime * result + ((productType == null) ? 0 : productType.hashCode());
	        return result;
	    }
	
	
}
