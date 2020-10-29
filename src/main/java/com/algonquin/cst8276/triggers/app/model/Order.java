package com.algonquin.cst8276.triggers.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDERS_ID")
    @SequenceGenerator(name = "SEQ_ORDERS_ID", sequenceName = "SEQ_ORDERS_ID")
    private Long id;

    @Min(1)
    private Integer quantity;

    @Generated(GenerationTime.ALWAYS)
    @Column(insertable = false, updatable = false)
    private BigDecimal orderPrice;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "tstmp", insertable = false, updatable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [id=");
        builder.append(id);
        builder.append(", quantity=");
        builder.append(quantity);
        builder.append(", orderPrice=");
        builder.append(orderPrice);
        builder.append(", tstmp=");
        builder.append(timestamp);
        builder.append(", product=");
        builder.append(product);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return 2020;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((Order) obj).getId());
    }
}
