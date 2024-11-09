package com.Timperio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Timperio.service.impl.PurchaseHistoryExportService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/export")
public class CsvController {
    @Autowired
    private PurchaseHistoryExportService purchaseHistoryExportService;

    @GetMapping
    public void exportPurchaseHistoryToCsv(@RequestParam(required = false) Integer customerId,
            HttpServletResponse response) throws Exception {
        this.purchaseHistoryExportService.writePurchaseHistoriesToCsv(customerId, response);
    }

}
