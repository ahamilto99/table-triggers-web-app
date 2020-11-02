package com.algonquin.cst8276.triggers.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.algonquin.cst8276.triggers.app.model.Order;
import com.algonquin.cst8276.triggers.app.model.dto.OrderProjection;
import com.algonquin.cst8276.triggers.app.repository.OrderRepository;
import com.algonquin.cst8276.triggers.app.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderProjection findOrderDtoById(Long orderId) {
        OrderProjection orderProjection = orderRepository.findDtoById(orderId);

        if (orderProjection == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\n\nOrder id=" + orderId + " does not exist\n\n");

        return orderProjection;
    }

    @Override
    public List<OrderProjection> findAllOrderDtos() {
        return orderRepository.findAllDtos();
    }

    @Override
    public void createOrder(String customer, Integer quantity, Long productId) {
        orderRepository.insert(customer, quantity, productId);
    }

    @Override
    public void updateOrder(Long orderId, Integer newQuantity) {
        orderRepository.update(orderId, newQuantity);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.delete(orderId);
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "\n\nOrder id=" + orderId + " does not exist\n\n"));
    }

}
