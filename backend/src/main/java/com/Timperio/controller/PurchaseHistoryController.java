package com.Timperio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timperio.enums.ChannelType;
import com.Timperio.enums.SalesType;
import com.Timperio.enums.ShippingMethod;
import com.Timperio.models.PurchaseHistory;
import com.Timperio.service.impl.PurchaseHistoryService;

@RestController
@RequestMapping("/api/v1/purchaseHistory")
@CrossOrigin(origins = "http://localhost:5174")

public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @GetMapping()
    public Iterable<PurchaseHistory> getAllSalesData() {
        return this.purchaseHistoryService.findAll();
    }

    @GetMapping("/customerId/{customerId}")
    public Iterable<PurchaseHistory> getCustomerSalesData(@PathVariable Integer customerId) {
        return this.purchaseHistoryService.findByCustomerId(customerId);
    }

    @GetMapping("/salesType/{salesType}")
    public Iterable<PurchaseHistory> getSalesDataByType(@PathVariable SalesType salesType) {
        return this.purchaseHistoryService.findBySalesType(salesType);
    }

    @GetMapping("/channelType/{channelType}")
    public Iterable<PurchaseHistory> getSalesDataByChannel(@PathVariable ChannelType channelType) {
        return this.purchaseHistoryService.findByChannelType(channelType);
    }

    @GetMapping("/shippingMethod/{shippingMethod}")
    public Iterable<PurchaseHistory> getSalesDataByShipping(@PathVariable ShippingMethod shippingMethod) {
        return this.purchaseHistoryService.findByShippingMethod(shippingMethod);
    }

}
