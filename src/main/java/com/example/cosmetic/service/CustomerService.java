package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers(); // Đây chính là hàm đang báo đỏ!
    void addCustomer(Customer customer) throws Exception;
    void updateCustomer(Customer customer) throws Exception;
    void deleteCustomer(Long id) throws Exception;
}