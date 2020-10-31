package com.algonquin.cst8276.triggers.app.service;

import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;

public interface ProductService {

    ProductProjection findProductProjectionById(Long productId);

}
