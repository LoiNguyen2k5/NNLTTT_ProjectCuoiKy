package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.repository.StaffRepository;
import com.example.cosmetic.service.AuthService;

public class AuthServiceImpl implements AuthService {
    private final StaffRepository staffRepository;

    // Dependency Injection
    public AuthServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff login(String username, String password) throws Exception {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new Exception("Vui lòng nhập đầy đủ Tài khoản và Mật khẩu!");
        }

        Staff staff = staffRepository.findByUsername(username);
        if (staff == null) {
            throw new Exception("Tài khoản không tồn tại!");
        }

        if (!staff.getPassword().equals(password)) {
            throw new Exception("Sai mật khẩu!");
        }

        return staff;
    }
}