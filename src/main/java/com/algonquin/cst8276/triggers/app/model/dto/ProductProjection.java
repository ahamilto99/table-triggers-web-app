package com.algonquin.cst8276.triggers.app.model.dto;

import java.math.BigDecimal;

public interface ProductProjection {

    Long getId();
    
    String getName();
    
    Long getCount();
    
    BigDecimal getPrice();
    
}
