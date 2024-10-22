package com.Timperio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timperio.models.Customer;
import com.Timperio.models.PurchaseHistory;
import com.Timperio.service.impl.CustomerService;
import com.Timperio.service.impl.PurchaseHistoryService;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    public PurchaseHistoryService purchaseHistoryService;

    public Iterable<Customer> getAllCustomers() {
        Iterable<PurchaseHistory> purchaseHistories = purchaseHistoryService.findAll();
        Map<Integer, Customer> customerMap = new HashMap<>();

        for (PurchaseHistory purchaseHistory : purchaseHistories) {
            Integer customerId = purchaseHistory.getCustomerId();
            if (!customerMap.containsKey(customerId)) {
                Customer customer = new Customer();
                customer.setCustomerId(customerId);
                customerMap.put(customerId, customer);
            }
        }

        Iterable<Customer> customers = new ArrayList<>(customerMap.values());

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
}
