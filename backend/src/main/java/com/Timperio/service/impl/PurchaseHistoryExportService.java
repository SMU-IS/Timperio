package com.Timperio.service.impl;

import com.Timperio.enums.SalesType;

import jakarta.servlet.http.HttpServletResponse;

public interface PurchaseHistoryExportService {
    public void writePurchaseHistoriesToCsv(Integer customerId, SalesType salesType,
            HttpServletResponse response)
            throws Exception;
}
