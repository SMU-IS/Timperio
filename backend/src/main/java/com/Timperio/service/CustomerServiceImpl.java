package com.Timperio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timperio.constant.CustomerConstant;
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

    @Transactional
    public void populateCustomersFromHistoryPurchases() {
        Iterable<PurchaseHistory> purchaseHistories = purchaseHistoryService.findAll();

        for (PurchaseHistory purchaseHistory : purchaseHistories) {
            Customer customer = purchaseHistory.getCustomer();
            Integer customerId = customer.getCustomerId();
            Iterable<PurchaseHistory> specificCustomerPurchaseHistory = this.purchaseHistoryService
                    .findByCustomerId(customerId);

            String customerEmail = String.format("jane_doe_%d@yopmail.com", customerId);
            Double totalSpendingByCustomer = this.purchaseHistoryService.getSalesTotal(specificCustomerPurchaseHistory);
            String customerSegment = CustomerSegment.LOW_SPEND.toString();

            if (!customerRepository.existsByCustomerId(customerId)) {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerId(customerId);
                newCustomer.setCustomerEmail(customerEmail);
                newCustomer.setTotalSpending(totalSpendingByCustomer);

                String sql = "INSERT INTO customer (customer_id, customer_email, total_spending, customer_segment) " +
                        "VALUES (:customerId, :email, :totalSpending, :customerSegment)";

                entityManager.createNativeQuery(sql)
                        .setParameter("customerId", customerId)
                        .setParameter("email", customerEmail)
                        .setParameter("totalSpending", totalSpendingByCustomer)
                        .setParameter("customerSegment", customerSegment)
                        .executeUpdate();
            }
        }

        this.sortCustomerIntoSegment();
    }

    @Override
    public void sortCustomerIntoSegment() {
        Iterable<Customer> customers = customerRepository.findAll();

        for (Customer customer : customers) {
            Double amountSpent = customer.getTotalSpending();
            CustomerSegment customerSegment;

            if (amountSpent == null || amountSpent == 0.0) {
                customerSegment = CustomerSegment.LOW_SPEND;
            } else if (amountSpent >= CustomerConstant.HIGH_VALUE_THRESHOLD) {
                customerSegment = CustomerSegment.HIGH_VALUE;
            } else if (amountSpent >= CustomerConstant.MID_TIER_THRESHOLD) {
                customerSegment = CustomerSegment.MID_TIER;
            } else {
                customerSegment = CustomerSegment.LOW_SPEND;
            }

            customer.setCustomerSegment(customerSegment);
            customerRepository.save(customer);
        }
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Override
    public Customer getCustomer(Integer customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        return customer;
    }

}
