package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    List<Customer> searchCustomerByPhone(String phone);
    void addCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer) throws Exception;
    void deleteCustomer(Long id) throws Exception;
}