package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Customer;
import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();
    Customer findById(Long id);
    void save(Customer customer);
    void update(Customer customer);
    void delete(Long id);
}