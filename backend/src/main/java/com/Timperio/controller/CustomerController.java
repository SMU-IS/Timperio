package com.Timperio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Timperio.enums.SuccessMessage;
import com.Timperio.models.Customer;
import com.Timperio.service.impl.CustomerService;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/populateCustomerDb")
    public SuccessMessage populateCustomers() {
        this.customerService.populateCustomersFromHistoryPurchases();
        return SuccessMessage.CUSTOMER_DB_POPULATED;
    }

    @GetMapping("/customers")
    public Iterable<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable() Integer customerId) {
        return this.customerService.getCustomer(customerId);
    }

}
