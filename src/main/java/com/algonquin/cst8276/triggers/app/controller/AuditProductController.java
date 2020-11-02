package com.algonquin.cst8276.triggers.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.algonquin.cst8276.triggers.app.service.AuditProductService;

@Controller
public class AuditProductController {
    
    private AuditProductService auditProductService;
    
    @Autowired
    public AuditProductController(AuditProductService auditProductService) {
        this.auditProductService = auditProductService;
    }
    
    @GetMapping("inventory/history")
    public String inventoryHistoryGet(Model model) {
        model.addAttribute("audits", auditProductService.findAllAuditProductDtos());
        return "product/history";
    }

}
