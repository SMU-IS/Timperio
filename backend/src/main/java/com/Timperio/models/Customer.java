package com.Timperio.models;

import com.Timperio.enums.CustomerSegment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    private Integer customerId;
    private String customerEmail;
    private Iterable<PurchaseHistory> purchaseHistory;
    private Double totalSpending;
    private CustomerSegment customerSegment;
}
