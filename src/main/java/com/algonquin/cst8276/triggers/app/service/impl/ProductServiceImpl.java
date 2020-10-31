package com.algonquin.cst8276.triggers.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;
import com.algonquin.cst8276.triggers.app.repository.ProductRepository;
import com.algonquin.cst8276.triggers.app.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
    @Override
    public ProductProjection findProductProjectionById(Long productId) {
        return productRepository.findProjectionById(productId);
    }


}
