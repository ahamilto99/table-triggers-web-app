package com.algonquin.cst8276.triggers.app.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTS_ID")
    @SequenceGenerator(name = "SEQ_PRODUCTS_ID", sequenceName = "SEQ_PRODUCTS_ID", allocationSize = 1)
    private Long id;
    
    @NotBlank
    private String name;
    
    @Min(0)
    @Column(name = "inventory_count")
    private Integer count;
    
    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Product [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", count=");
        builder.append(count);
        builder.append(", unitPrice=");
        builder.append(unitPrice);
        builder.append("]");
        return builder.toString();
    }
}
