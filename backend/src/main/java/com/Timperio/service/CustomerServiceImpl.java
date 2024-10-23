package com.Timperio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timperio.enums.CustomerSegment;
import com.Timperio.models.Customer;
import com.Timperio.models.PurchaseHistory;
import com.Timperio.repository.CustomerRepository;
import com.Timperio.service.impl.CustomerService;
import com.Timperio.service.impl.PurchaseHistoryService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public PurchaseHistoryService purchaseHistoryService;

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    private EntityManager entityManager;

    Map<Integer, Customer> customerMap = new HashMap<>();

    List<Customer> highValueCustomers = new ArrayList<>();
    List<Customer> midTierCustomers = new ArrayList<>();
    List<Customer> lowSpendCustomers = new ArrayList<>();

    @Transactional
    public void populateCustomersFromHistoryPurchases() {
        Iterable<PurchaseHistory> purchaseHistories = purchaseHistoryService.findAll();

        for (PurchaseHistory purchaseHistory : purchaseHistories) {
            Customer customer = purchaseHistory.getCustomer();
            Integer customerId = customer.getCustomerId();
            String email = String.format("jane_doe_%d@yopmail.com", customerId);
            Double totalSpending = 0.0;
            String customerSegment = CustomerSegment.LOW_SPEND.toString();

            if (!customerRepository.existsByCustomerId(customerId)) {
                String sql = "INSERT INTO customer (customer_id, customer_email, total_spending, customer_segment) " +
                        "VALUES (:customerId, :email, :totalSpending, :customerSegment)";

                entityManager.createNativeQuery(sql)
                        .setParameter("customerId", customerId)
                        .setParameter("email", email)
                        .setParameter("totalSpending", totalSpending)
                        .setParameter("customerSegment", customerSegment)
                        .executeUpdate();
            }
        }
    }

    public Iterable<Customer> getAllCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Customer getCustomer(Integer customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        return customer;
    }

    // public CustomerSegment getCustomerSegment(Customer customer) {
    // if (customer == null) {
    // throw new
    // IllegalArgumentException(ErrorMessage.NO_CUSTOMER_FOUND.toString());
    // }

    // Double amountSpent = customer.getTotalSpending();

    // if (amountSpent == null) {
    // return CustomerSegment.LOW_SPEND;
    // }

    // if (amountSpent >= CustomerConstant.HIGH_VALUE_THRESHOLD) {
    // return CustomerSegment.HIGH_VALUE;
    // }
    // if (amountSpent >= CustomerConstant.MID_TIER_THRESHOLD) {
    // return CustomerSegment.MID_TIER;
    // }

    // return CustomerSegment.LOW_SPEND;
    // }

    // @Override
    // public void sortCustomerIntoSegment() {
    // Iterable<Customer> customers = customerRepository.findAll();

    // for (Customer customer : customers) {
    // Double amountSpent = customer.getTotalSpending();
    // if (amountSpent == null) {
    // lowSpendCustomers.add(customer);
    // continue;
    // }

    // if (amountSpent >= CustomerConstant.HIGH_VALUE_THRESHOLD) {
    // highValueCustomers.add(customer);
    // } else if (amountSpent >= CustomerConstant.MID_TIER_THRESHOLD) {
    // midTierCustomers.add(customer);
    // } else {
    // lowSpendCustomers.add(customer);
    // }
    // }
    // }
}
