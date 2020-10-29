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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "AUDIT_PRODUCTS")
public class AuditProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUDIT_PRODUCTS_ID")
    @SequenceGenerator(name = "SEQ_AUDIT_PRODUCTS_ID", sequenceName = "SEQ_AUDIT_PRODUCTS_ID")
    private Long id;

    @DecimalMin("0.01")
    @Column(updatable = false)
    private BigDecimal unitPrice;

    @Min(0)
    @Column(name = "INVENTORY_COUNT", updatable = false)
    private Integer count;

    @Column(updatable = false)
    private Integer inventoryChange;

    @Size(min = 6, max = 6)
    @Column(updatable = false)
    private String revisionType;

    @Column(updatable = false)
    private LocalDateTime revisionTstmp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public int hashCode() {
        return 2020;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        return id != null && id.equals(((AuditProduct) obj).getId());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuditProduct [id=");
        builder.append(id);
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
