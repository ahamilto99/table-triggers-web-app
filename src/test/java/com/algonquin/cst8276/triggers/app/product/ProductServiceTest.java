package com.algonquin.cst8276.triggers.app.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.algonquin.cst8276.triggers.app.model.Order;
import com.algonquin.cst8276.triggers.app.model.Product;
import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;
import com.algonquin.cst8276.triggers.app.repository.OrderRepository;
import com.algonquin.cst8276.triggers.app.repository.ProductRepository;
import com.algonquin.cst8276.triggers.app.service.ProductService;

@SpringBootTest
public class ProductServiceTest {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    void testFindProductProjectionById() {
        ProductProjection productProjection = productService.findProductProjectionById(1L);
        
        assertEquals("Widget", productProjection.getName());
        assertEquals(1_000, productProjection.getCount());
        assertEquals(new BigDecimal(1.25), productProjection.getPrice());
    }

}
