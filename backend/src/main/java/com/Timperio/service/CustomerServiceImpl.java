package com.Timperio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timperio.constant.CustomerConstant;
import com.Timperio.models.Customer;
import com.Timperio.models.PurchaseHistory;
import com.Timperio.service.impl.CustomerService;
import com.Timperio.service.impl.PurchaseHistoryService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public PurchaseHistoryService purchaseHistoryService;

    Map<Integer, Customer> customerMap = new HashMap<>();
    List<Customer> customers = new ArrayList<>();
    List<Customer> highValueCustomers = new ArrayList<>();
    List<Customer> midTierCustomers = new ArrayList<>();
    List<Customer> lowSpendCustomers = new ArrayList<>();

    public Iterable<Customer> getAllCustomers() {
        Iterable<PurchaseHistory> purchaseHistories = purchaseHistoryService.findAll();

        for (PurchaseHistory purchaseHistory : purchaseHistories) {
            Integer customerId = purchaseHistory.getCustomerId();
            if (!customerMap.containsKey(customerId)) {
                Customer customer = new Customer();
                customer.setCustomerId(customerId);
                customerMap.put(customerId, customer);
            }
        }

        customers.addAll(customerMap.values());

        // Sample output
        /**
         * [
         * {
         * "customerId": 1,
         * "customerEmail": null,
         * "purchaseHistory": null,
         * "totalSpending": null,
         * "customerSegment": null
         * },
         * {
         * "customerId": 2,
         * "customerEmail": null,
         * "purchaseHistory": null,
         * "totalSpending": null,
         * "customerSegment": null
         * },
         */

        return customers;
    }

    @Override
    public Iterable<Customer> getHighValueCustomers() {
        for (Customer customer : customers) {
            if (customer.getTotalSpending() >= CustomerConstant.HIGH_VALUE_THRESHOLD) {
                highValueCustomers.add(customer);
            }
        }
        return highValueCustomers;
    }

    @Override
    public Iterable<Customer> getMidTierCustomers() {
        for (Customer customer : customers) {
            Double amountSpent = customer.getTotalSpending();
            if (amountSpent >= CustomerConstant.MID_TIER_THRESHOLD
                    && amountSpent <= CustomerConstant.HIGH_VALUE_THRESHOLD) {
                midTierCustomers.add(customer);
            }
        }
        return midTierCustomers;
    }

    @Override
    public Iterable<Customer> getLowSpentCustomers() {
        for (Customer customer : customers) {
            Double amountSpent = customer.getTotalSpending();
            if (amountSpent <= CustomerConstant.LOW_SPENT_THRESHOLD) {
                lowSpendCustomers.add(customer);
            }
        }
        return midTierCustomers;
    }
}
