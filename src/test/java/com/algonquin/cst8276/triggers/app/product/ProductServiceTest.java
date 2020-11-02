package com.algonquin.cst8276.triggers.app.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.algonquin.cst8276.triggers.app.model.AuditProduct;
import com.algonquin.cst8276.triggers.app.model.Product;
import com.algonquin.cst8276.triggers.app.model.dto.AuditProductProjection;
import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;
import com.algonquin.cst8276.triggers.app.repository.AuditProductRepository;
import com.algonquin.cst8276.triggers.app.repository.ProductRepository;
import com.algonquin.cst8276.triggers.app.service.ProductService;

@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuditProductRepository auditProductRepository;

    @Test
    void testFindDtoById() {
        ProductProjection productProjection = productService.findProductDtoById(1L);

        assertEquals("Widget", productProjection.getName());
        assertEquals((long) productRepository.findById(1L).get().getCount(), productProjection.getCount());
        assertEquals(new BigDecimal(1.25), productProjection.getPrice());
    }

    @Test
    void testFindAllProductDtos() {
        long numProducts = productRepository.count();

        assertEquals(numProducts, productService.findAllProductDtos().size());
    }

    @Test
    void testFindAllProductNames() {
        List<ProductProjection> productDtos = productService.findAllProductNamesPrices();

        assertEquals(productRepository.count(), productDtos.size());

        productDtos.forEach(p -> assertNotNull(p.getName()));
    }

    @Test
    void testAddInventoryForOneProduct() {
        int numToAdd = 15;
        Long productId = 1L;
        Long initialCount = productService.findProductDtoById(productId).getCount();

        productService.addInventoryForOneProduct(productId, numToAdd);

        ProductProjection productAfterAddition = productService.findProductDtoById(productId);

        assertEquals(initialCount + numToAdd, productAfterAddition.getCount());

        AuditProductProjection auditProductAfterAddition = auditProductRepository.findMostRecentRevision(productId);

        assertEquals(numToAdd, auditProductAfterAddition.getCountChange());
        assertEquals(productAfterAddition.getName(), auditProductAfterAddition.getName());
        assertEquals(productAfterAddition.getCount(), auditProductAfterAddition.getCount());
        assertEquals(productAfterAddition.getPrice(), auditProductAfterAddition.getPrice());
        assertEquals(productAfterAddition.getId(), auditProductAfterAddition.getProductId());
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteProduct() {
        long productCount = productRepository.count();
        long numDeletes = auditProductRepository.findNumDeletes();
        Long productId = 3L;

        assertFalse(auditProductRepository.checkIfDeleted(productId));

        productService.deleteProductById(productId);

        assertTrue(auditProductRepository.checkIfDeleted(productId));
        assertEquals(productCount - 1, productRepository.count());
        assertEquals(numDeletes + 1, auditProductRepository.findNumDeletes());
    }

    @Test
    void testCreateProduct() {
        long productCount = productRepository.count();
        long insertCount = auditProductRepository.findNumInserts();

        String name = "Keyboard";
        Integer count = 1000;
        BigDecimal price = BigDecimal.valueOf(25.99);

        productService.createProduct(name, count, price);

        Product newProduct = productRepository.findAll().stream().max((p1, p2) -> p1.getId().compareTo(p2.getId()))
                .get();

        assertEquals(name, newProduct.getName());
        assertEquals(count, newProduct.getCount());
        assertTrue(price.equals(newProduct.getUnitPrice()));

        AuditProduct ap = auditProductRepository.findByProductId(newProduct.getId());

        assertEquals(name, ap.getName());
        assertEquals(count, ap.getCount());
        assertEquals(count, ap.getInventoryChange());
        assertTrue(price.equals(ap.getUnitPrice()));
        assertEquals("INSERT", ap.getRevisionType());

        assertEquals(productCount + 1, productRepository.count());
        assertEquals(insertCount + 1, auditProductRepository.findNumInserts());
    }

}
