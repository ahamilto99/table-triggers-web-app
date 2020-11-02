package com.algonquin.cst8276.triggers.app.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        ProductProjection productProjection = productRepository.findDtoById(productId);

        if (productProjection == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\n\nProduct id= " + productId + " does not exist\n\n");

        return productProjection;
    }

    @Override
    public List<ProductProjection> findAllProductDtos() {
        return productRepository.findAllDtos();
    }

    @Override
    public List<ProductProjection> findAllProductNamesPrices() {
        return productRepository.findAllNamesPrices();
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
        productRepository.insertProduct(name, count, unitPrice);
    }

}
