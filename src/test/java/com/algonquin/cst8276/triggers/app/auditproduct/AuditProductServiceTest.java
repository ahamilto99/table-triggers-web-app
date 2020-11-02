package com.algonquin.cst8276.triggers.app.auditproduct;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.algonquin.cst8276.triggers.app.repository.ProductRepository;
import com.algonquin.cst8276.triggers.app.service.AuditProductService;

@SpringBootTest
@ActiveProfiles("test")
public class AuditProductServiceTest {

    @Autowired
    private AuditProductService auditProductService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testAuditProductInsertAndDeleteCount() {
        System.out.println(LocalDateTime.now());

        long numProducts = productRepository.count();

        long numAuditProductInserts = auditProductService.findNumProductInserts();

        assertTrue(numAuditProductInserts > 0);

        long numAuditProductDeletes = auditProductService.findNumProductDeletes();

        assertEquals(numProducts, numAuditProductInserts - numAuditProductDeletes);
    }

}
