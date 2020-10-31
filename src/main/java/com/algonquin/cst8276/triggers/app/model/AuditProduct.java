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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Immutable;


@Entity
@Immutable
@Table(name = "AUDIT_PRODUCTS")
public class AuditProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUDIT_PRODUCTS_ID")
    @SequenceGenerator(name = "SEQ_AUDIT_PRODUCTS_ID", sequenceName = "SEQ_AUDIT_PRODUCTS_ID", allocationSize = 1)
    private Long id;
    
    @NotNull
    @Size(max = 50)
    private String name;

    @DecimalMin("0.01")
    private BigDecimal unitPrice;

    @Min(0)
    @Column(name = "INVENTORY_COUNT")
    private Integer count;

    private Integer inventoryChange;

    @Size(min = 6, max = 6)
    private String revisionType;

    private LocalDateTime revisionTstmp;

    @Column(name = "product_id", nullable = false)
    private Product product;

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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getInventoryChange() {
        return inventoryChange;
    }

    public void setInventoryChange(Integer inventoryChange) {
        this.inventoryChange = inventoryChange;
    }

    public String getRevisionType() {
        return revisionType;
    }

    public void setRevisionType(String revisionType) {
        this.revisionType = revisionType;
    }

    public LocalDateTime getRevisionTstmp() {
        return revisionTstmp;
    }

    public void setRevisionTstmp(LocalDateTime revisionTstmp) {
        this.revisionTstmp = revisionTstmp;
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
        builder.append("AuditProduct [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", unitPrice=");
        builder.append(unitPrice);
        builder.append(", count=");
        builder.append(count);
        builder.append(", inventoryChange=");
        builder.append(inventoryChange);
        builder.append(", revisionType=");
        builder.append(revisionType);
        builder.append(", revisionTstmp=");
        builder.append(revisionTstmp);
        builder.append("]");
        return builder.toString();
    }

}
