package com.algonquin.cst8276.triggers.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERS_ID")
    @SequenceGenerator(name = "SEQ_ORDERS_ID", sequenceName = "SEQ_ORDERS_ID", allocationSize = 1)
    private Long id;
    
    @NotEmpty(message = "Please enter a name")
    @Size(max = 50, message = "Name cannot be longer than 50 characters")
    private String customerName;
    
    @Min(value = 1, message = "Quantity cannot be less than 1")
    private Integer quantity;

    @Generated(GenerationTime.ALWAYS)
    @Column(insertable = false, updatable = false)
    private BigDecimal orderPrice;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "tstmp", insertable = false, updatable = false)
    private LocalDateTime timestamp;

    @NotNull(message = "Please select a product")
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public LocalDateTime getTstmp() {
        return timestamp;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [id=");
        builder.append(id);
        builder.append(", customerName=");
        builder.append(customerName);
        builder.append(", quantity=");
        builder.append(quantity);
        builder.append(", orderPrice=");
        builder.append(orderPrice);
        builder.append(", tstmp=");
        builder.append(timestamp);
        builder.append("]");
        return builder.toString();
    }

}
