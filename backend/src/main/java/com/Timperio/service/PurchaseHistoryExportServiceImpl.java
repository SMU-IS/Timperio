package com.Timperio.service;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.Timperio.dto.PurchaseHistoryDto;
import com.Timperio.service.impl.PurchaseHistoryExportService;
import com.Timperio.service.impl.PurchaseHistoryService;
import com.opencsv.CSVWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PurchaseHistoryExportServiceImpl implements PurchaseHistoryExportService {

    private final PurchaseHistoryService purchaseHistoryService;

    public PurchaseHistoryExportServiceImpl(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @Override
    public void writePurchaseHistoriesToCsv(Integer customerId, HttpServletResponse response)
            throws Exception {
        List<PurchaseHistoryDto> purchaseHistories = purchaseHistoryService
                .findAllFilteredPurchaseHistories(customerId);

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"purchase_history.csv\"");
        try (PrintWriter writer = response.getWriter(); CSVWriter csvWriter = new CSVWriter(writer)) {

            String[] header = { "Customer Id", "Sales Type", "Total Price", "Sales Date"
            };
            csvWriter.writeNext(header);

            for (PurchaseHistoryDto history : purchaseHistories) {
                String[] data = {
                        String.valueOf(history.getCustomerId()),
                        history.getSalesType().toString(),
                        String.valueOf(history.getTotalPrice()),
                        history.getSalesDate().toString()
                };
                csvWriter.writeNext(data);
            }
        }
    }
}
