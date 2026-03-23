package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.repository.CustomerRepository;
import com.example.cosmetic.service.CustomerService;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repo;

    public CustomerServiceImpl(CustomerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @Override
    public void addCustomer(Customer c) throws Exception {
        if (c.getName() == null || c.getName().isEmpty()) throw new Exception("Tên khách hàng không được để trống!");
        repo.save(c);
    }

    @Override
    public void updateCustomer(Customer c) throws Exception { repo.update(c); }

    @Override
    public void deleteCustomer(Long id) throws Exception { repo.delete(id); }
}