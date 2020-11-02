package com.algonquin.cst8276.triggers.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algonquin.cst8276.triggers.app.model.Order;
import com.algonquin.cst8276.triggers.app.model.dto.OrderProjection;

@Repository
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, Long> { // @formatter:off
    
    @Query("SELECT o.id AS id, o.customerName AS customer, o.quantity AS quantity, o.orderPrice AS price, o.timestamp AS time,"
                + " p.id AS productId, p.name AS product"
            + " FROM Order o"
            + " LEFT JOIN FETCH Product p"
            + " ON o.productId = p.id"
            + " WHERE o.id = ?1")
    OrderProjection findDtoById(Long orderId);
    
    @Query("SELECT o.id AS id, o.customerName AS customer, o.quantity AS quantity, o.orderPrice AS price, o.timestamp AS time,"
                + " p.id AS productId, p.name AS product"
            + " FROM Order o"
            + " LEFT JOIN FETCH Product p"
            + " ON o.productId = p.id"
            + " ORDER BY time DESC")
    List<OrderProjection> findAllDtos();
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO C##AL.ORDERS (ID, CUSTOMER_NAME, QUANTITY, PRODUCT_ID) VALUES "
            + "(C##AL.SEQ_ORDERS_ID.NEXTVAL, :customer, :quantity, :product)", nativeQuery = true)
    void insert(@Param("customer") String customer, @Param("quantity") Integer quantity, @Param("product") Long productId);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE C##AL.ORDERS SET QUANTITY = :qty"
            + " WHERE ID = :id", nativeQuery = true)
    void update(@Param("id") Long orderId, @Param("qty") Integer newQuantity);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM C##AL.ORDERS"
            + " WHERE ID = ?1", nativeQuery = true)
    void delete(Long orderId);

}   // @formatter:off
