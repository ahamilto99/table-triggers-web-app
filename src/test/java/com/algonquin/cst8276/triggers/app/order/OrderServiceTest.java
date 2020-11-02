package com.algonquin.cst8276.triggers.app.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.algonquin.cst8276.triggers.app.model.dto.AuditProductProjection;
import com.algonquin.cst8276.triggers.app.model.dto.OrderProjection;
import com.algonquin.cst8276.triggers.app.model.dto.ProductProjection;
import com.algonquin.cst8276.triggers.app.repository.AuditProductRepository;
import com.algonquin.cst8276.triggers.app.repository.OrderRepository;
import com.algonquin.cst8276.triggers.app.service.OrderService;
import com.algonquin.cst8276.triggers.app.service.ProductService;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuditProductRepository auditProductRepository;

    @Test
    void testFindOrderDto() {
        OrderProjection orderProjection = orderService.findOrderDtoById(1L);

        assertEquals("John Wayne", orderProjection.getCustomer());
        assertEquals(3, orderProjection.getProductId());
    }

    @Test
    void testFindAllOrderDtos() {
        long orderCount = orderRepository.count();

        assertEquals(orderCount, orderService.findAllOrderDtos().size());
    }

    @Test
    void testCreateOrder() {
        long orderCount = orderRepository.count();
        long numInventoryUpdates = auditProductRepository.findNumUpdates();

        String customer = "Kim Smith";
        Integer quantity = 5;
        Long productId = 2L;

        Long productCountBeforeInsert = productService.findProductDtoById(productId).getCount();

        orderService.createOrder(customer, quantity, productId);

        assertEquals(orderCount + 1, orderRepository.count());
        assertEquals(numInventoryUpdates + 1, auditProductRepository.findNumUpdates());

        OrderProjection orderProjection = orderService.findAllOrderDtos().stream()
                .max((o1, o2) -> o1.getId().compareTo(o2.getId())).get();

        assertEquals(customer, orderProjection.getCustomer());
        assertEquals(quantity, orderProjection.getQuantity());
        assertEquals(productId, orderProjection.getProductId());
        assertNotNull(orderProjection.getTime());

        assertEquals(productCountBeforeInsert - quantity, productService.findProductDtoById(productId).getCount());

        AuditProductProjection auditProductProjection = auditProductRepository.findMostRecentRevision(productId);

        assertEquals("UPDATE", auditProductProjection.getRevType());
        assertEquals(-1L * quantity, auditProductProjection.getCountChange());
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateOrder() {
        Long orderId = 1L;
        Long productId = 3L;

        Long initProductCount = productService.findProductDtoById(productId).getCount();

        OrderProjection initOrder = orderService.findOrderDtoById(orderId);
        Integer initOrderQty = initOrder.getQuantity();

        Long numUpdates = auditProductRepository.findNumUpdates();

        Integer newQty = 99;

        orderService.updateOrder(orderId, newQty);

        ProductProjection updatedProduct = productService.findProductDtoById(productId);
        assertEquals(updatedProduct.getCount(), initProductCount - (newQty - initOrderQty));

        OrderProjection updatedOrder = orderService.findOrderDtoById(orderId);

        assertEquals(newQty, updatedOrder.getQuantity());
        assertEquals(updatedProduct.getPrice().multiply(BigDecimal.valueOf(newQty)), updatedOrder.getPrice());

        assertEquals(initProductCount - (newQty - initOrderQty),
                productService.findProductDtoById(productId).getCount());

        assertEquals(auditProductRepository.findMostRecentRevision(productId).getCountChange(), initOrderQty - newQty);
        assertEquals(numUpdates + 1, auditProductRepository.findNumUpdates());
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteOrder() {
        Long orderId = 1L;
        Long initOrderCount = orderRepository.count();

        OrderProjection order = orderService.findOrderDtoById(orderId);
        Integer orderQty = order.getQuantity();
        Long productId = productService.findAllProductNamesPrices().stream()
                .filter(p -> p.getName().equals(order.getProduct())).findFirst().get().getId();

        Long initProductCount = productService.findProductDtoById(productId).getCount();

        orderService.deleteOrder(orderId);

        assertEquals(initOrderCount - 1, orderRepository.count());

        assertEquals(productService.findProductDtoById(productId).getCount(), initProductCount + orderQty);

        assertEquals(auditProductRepository.findMostRecentRevision(productId).getCountChange().intValue(), orderQty);
    }

    @Test
    @Disabled("For normal testing the BUSINESS_HOURS_TRIGGER is DISABLED, therefore this will not succeed unless the trigger is"
            + " enabled")
    void testBusinessHoursTrigger() {
        DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        int hourOfDay = LocalDateTime.now().getHour();

        if (dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY) || hourOfDay < 9
                || hourOfDay > 17) {
            assertThrows(JpaSystemException.class, () -> orderService.createOrder("Alex", 1, 1L));
            assertThrows(JpaSystemException.class, () -> orderService.updateOrder(3L, 1));
            assertThrows(JpaSystemException.class, () -> orderService.deleteOrder(3L));
        }
    }
}
