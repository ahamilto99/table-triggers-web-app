package com.algonquin.cst8276.triggers.app.service;

import java.util.List;

import com.algonquin.cst8276.triggers.app.model.dto.AuditProductProjection;

public interface AuditProductService {

    long findNumProductInserts();

    long findNumProductDeletes();

    List<AuditProductProjection> findAllAuditProductDtos();

}
