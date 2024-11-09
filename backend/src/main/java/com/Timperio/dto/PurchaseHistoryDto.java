package com.Timperio.dto;

import com.Timperio.enums.SalesType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PurchaseHistoryDto {
    private Integer customerId;
    private SalesType salesType;
    private Double totalPrice;
    private String salesDate;
}
