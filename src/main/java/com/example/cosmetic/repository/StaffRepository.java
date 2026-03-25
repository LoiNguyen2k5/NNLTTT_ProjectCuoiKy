package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Staff;
import java.util.List;

public interface StaffRepository {
    List<Staff> findAll();
    Staff findByUsername(String username);
    void save(Staff staff) throws Exception;
    void update(Staff staff) throws Exception;
    void delete(Long id) throws Exception;
}