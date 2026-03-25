package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Staff;
import java.util.List;

public interface StaffService {
    List<Staff> getAllStaffs();
    void addStaff(Staff staff) throws Exception;
    void updateStaff(Staff staff) throws Exception;
    void deleteStaff(Long id) throws Exception;
}