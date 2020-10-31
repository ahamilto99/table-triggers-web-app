package com.algonquin.cst8276.triggers.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algonquin.cst8276.triggers.app.model.Order;

@Repository
@Transactional(readOnly =  true)
public interface OrderRepository extends JpaRepository<Order, Long> {

}
