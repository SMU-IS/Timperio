package com.Timperio.repository;

import org.springframework.data.repository.CrudRepository;

import com.Timperio.enums.CustomerSegment;
import com.Timperio.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Iterable<Customer> findAll();

    Customer findByCustomerId(Integer customerId);

    boolean existsByCustomerId(Integer customerId);

    Iterable<Customer> findByCustomerSegment(CustomerSegment CustomerSegment);
}
