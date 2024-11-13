package com.Timperio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.Timperio.enums.*;
import com.Timperio.service.impl.DataImportService;

@RestController
@RequestMapping("/api/v1/import-data")
public class DataImportController {

    @Autowired
    private DataImportService dataImportService;

    @PostMapping()
    public ResponseEntity<String> importSalesData() {
        try {
            dataImportService.importData("./src/main/resources/db/migration/sales_data.xlsx");
            return ResponseEntity.ok(SuccessMessage.DB_TABLES_POPULATED.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during data import: " + e.getMessage());
        }
    }
}
