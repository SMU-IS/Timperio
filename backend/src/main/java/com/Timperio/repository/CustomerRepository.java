package com.Timperio.repository;

import org.springframework.data.repository.CrudRepository;

import com.Timperio.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Iterable<Customer> findAll();

    boolean existsByCustomerId(Integer customerId);
}
