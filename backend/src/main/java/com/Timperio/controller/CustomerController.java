package com.Timperio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timperio.enums.SuccessMessage;
import com.Timperio.models.Customer;
import com.Timperio.service.impl.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/populateCustomerDb")
    public SuccessMessage populateCustomers() {
        this.customerService.populateCustomersFromHistoryPurchases();
        return SuccessMessage.CUSTOMER_DB_POPULATED;
    }

    @GetMapping()
    public Iterable<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

}
