package com.algonquin.cst8276.triggers.app.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface AuditProductProjection {

    Long getId();
    
    String getName();
    
    BigDecimal getPrice();
    
    Long getCount();
    
    Long getCountChange();
    
    String getRevType();
    
    LocalDateTime getRevTstmp();
    
    Long getProductId();
    
    Boolean isDeleted();
}
