package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Staff;

public interface StaffRepository {
    Staff findByUsername(String username);
}