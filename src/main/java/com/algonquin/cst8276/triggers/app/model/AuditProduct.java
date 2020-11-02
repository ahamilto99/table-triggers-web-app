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

import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Table(name = "AUDIT_PRODUCTS")
public class AuditProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUDIT_PRODUCTS_ID")
    @SequenceGenerator(name = "SEQ_AUDIT_PRODUCTS_ID", sequenceName = "SEQ_AUDIT_PRODUCTS_ID", allocationSize = 1)
    private Long id;

    private String name;

    private BigDecimal unitPrice;

    @Column(name = "INVENTORY_COUNT")
    private Integer count;

    private Integer inventoryChange;

    private String revisionType;

    private LocalDateTime revisionTstmp;

    private Long productId;

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

    public Long getProduct() {
        return productId;
    }

    public void setProduct(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuditProduct [id=");
        builder.append(id);
        builder.append(", productId=");
        builder.append(productId);
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
