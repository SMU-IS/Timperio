package com.Timperio.service.impl;

import java.time.LocalDate;

import com.Timperio.enums.SalesType;

import jakarta.servlet.http.HttpServletResponse;

public interface PurchaseHistoryExportService {
    public void writePurchaseHistoriesToCsv(Integer customerId, SalesType salesType, LocalDate salesDate,
            HttpServletResponse response)
            throws Exception;
}
