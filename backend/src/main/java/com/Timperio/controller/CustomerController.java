package com.Timperio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Timperio.enums.CustomerSegment;
import com.Timperio.enums.SuccessMessage;
import com.Timperio.models.Customer;
import com.Timperio.models.Metric;
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

    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Integer customerId) {
        return this.customerService.getCustomer(customerId);
    }

    @GetMapping("/segment/{customerSegment}")
    public Iterable<Customer> getCustomerByCustomerSegment(@PathVariable CustomerSegment customerSegment) {
        return this.customerService.getCustomerByCustomerSegment(customerSegment);
    }

    @GetMapping("/metrics")
    public Metric getMetrics() {
        return this.customerService.getMetrics();
    }

    @GetMapping("/metrics/{customerId}")
    public Metric getMetricsByCustomerId(@PathVariable Integer customerId) {
        return this.customerService.getMetricsByCustomer(customerId);
    }

}
