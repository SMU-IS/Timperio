package com.Timperio.service.impl;

// import com.Timperio.enums.CustomerSegment;
import com.Timperio.models.Customer;

public interface CustomerService {

    public void populateCustomersFromHistoryPurchases();

    public Iterable<Customer> getAllCustomers();

    public Customer getCustomer(Integer customerId);

    // public void sortCustomerIntoSegment();

    // public Iterable<Customer> getCustomerSegment(CustomerSegment
    // customerSegment);

    // String getMetrics(Iterable<Customer> customers);

    // Integer getTotalNumberOfSales(Iterable<Customer> customers);

    // Double getTotalAmountOfSales(Iterable<Customer> customers);

    // Double getAverageOrderValue(Iterable<Customer> customers);

}
