package com.algonquin.cst8276.triggers.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algonquin.cst8276.triggers.app.repository.AuditProductRepository;
import com.algonquin.cst8276.triggers.app.service.AuditProductService;

@Service
public class AuditProductServiceImpl implements AuditProductService {

    private AuditProductRepository auditProductRepository;

    @Autowired
    public AuditProductServiceImpl(AuditProductRepository auditProductRepository) {
        this.auditProductRepository = auditProductRepository;
    }

    @Override
    public long findNumProductInserts() {
        return auditProductRepository.findNumInserts();
    }

    @Override
    public long findNumProductDeletes() {
        return auditProductRepository.findNumDeletes();
    }

}
