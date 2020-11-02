package com.algonquin.cst8276.triggers.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;

public interface ProductService {

    ProductProjection findProductDtoById(Long productId);

    List<ProductProjection> findAllProductDtos();

    List<ProductProjection> findAllProductNamesPrices();

    void addInventoryForOneProduct(Long productId, int numToAdd);

    void deleteProductById(Long productId);

    void createProduct(String name, Integer count, BigDecimal unitPrice);

}
