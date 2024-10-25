package com.Timperio.service.impl;

import com.Timperio.enums.CustomerSegment;
import com.Timperio.models.Customer;
import com.Timperio.models.Metric;

public interface CustomerService {

    public void populateCustomersFromHistoryPurchases();

    public void sortCustomerIntoSegment();

    public Iterable<Customer> getAllCustomers();

    public Customer getCustomer(Integer customerId);

    public Iterable<Customer> getCustomerByCustomerSegment(CustomerSegment customerSegment);

    public Metric getMetrics();

    public Metric getMetricsByCustomer(Integer customerId);

}
