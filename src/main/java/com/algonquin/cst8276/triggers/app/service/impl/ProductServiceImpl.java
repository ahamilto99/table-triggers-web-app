package com.algonquin.cst8276.triggers.app.service.impl;

import java.math.BigDecimal;
import java.util.List;

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
    public ProductProjection findProductDtoById(Long productId) {
        return productRepository.findDtoById(productId);
    }

    @Override
    public List<ProductProjection> findAllProductDtos() {
        return productRepository.findAllDtos();
    }

    @Override
    public List<ProductProjection> findAllProductNames() {
        return productRepository.findAllNames();
    }

    @Override
    public void addInventoryForOneProduct(Long productId, int numToAdd) {
        productRepository.addInventory(productId, numToAdd);
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.deleteProduct(productId);
    }

    @Override
    public void createProduct(String name, Integer count, BigDecimal unitPrice) {
        productRepository.createProduct(name, count, unitPrice);
    }

}
