package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.repository.CustomerRepository;
import com.example.cosmetic.service.CustomerService;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repo;

    public CustomerServiceImpl(CustomerRepository repo) { this.repo = repo; }

    @Override public List<Customer> getAllCustomers() { return repo.findAll(); }
    @Override public List<Customer> searchCustomerByPhone(String phone) { return repo.searchByPhone(phone); }

    @Override
    public void addCustomer(Customer c) throws Exception {
        validateCustomer(c);
        
        // Check trùng số điện thoại
        for (Customer existing : repo.findAll()) {
            if (existing.getPhoneNumber().equals(c.getPhoneNumber())) {
                throw new Exception("Số điện thoại này đã tồn tại trong hệ thống!");
            }
        }
        repo.save(c);
    }

    @Override
    public void updateCustomer(Customer c) throws Exception { 
        validateCustomer(c);
        repo.update(c); 
    }

    @Override
    public void deleteCustomer(Long id) throws Exception { repo.delete(id); }

    // Hàm dùng chung để kiểm tra lỗi nhập liệu
    private void validateCustomer(Customer c) throws Exception {
        if (c.getFullName() == null || c.getFullName().trim().isEmpty()) 
            throw new Exception("Tên khách hàng không được để trống!");
        if (c.getPhoneNumber() == null || c.getPhoneNumber().trim().isEmpty()) 
            throw new Exception("Số điện thoại không được để trống!");
        if (!c.getPhoneNumber().matches("\\d{10}")) // Bắt buộc nhập 10 chữ số
            throw new Exception("Số điện thoại không hợp lệ (phải gồm 10 chữ số)!");
    }
}