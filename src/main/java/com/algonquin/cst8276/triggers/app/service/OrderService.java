package com.algonquin.cst8276.triggers.app.service;

import java.util.List;

import com.algonquin.cst8276.triggers.app.model.Order;
import com.algonquin.cst8276.triggers.app.model.dto.OrderProjection;

public interface OrderService {

    OrderProjection findOrderDtoById(Long orderId);

    List<OrderProjection> findAllOrderDtos();

    void createOrder(String customer, Integer quantity, Long productId);

    void updateOrder(Long orderId, Integer newQty);

    void deleteOrder(Long orderId);

    Order findOrderById(Long orderId);

}
