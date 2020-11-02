package com.algonquin.cst8276.triggers.app.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderProjection {
    
    Long getId();
    
    String getCustomer();
    
    Integer getQuantity();
    
    BigDecimal getPrice();
    
    LocalDateTime getTime();
    
    Long getProductId();
    
    String getProduct();

}
