package com.Timperio.service.impl;

import jakarta.servlet.http.HttpServletResponse;

public interface PurchaseHistoryExportService {
    public void writePurchaseHistoriesToCsv(Integer customerId, HttpServletResponse response)
            throws Exception;
}
