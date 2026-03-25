package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.repository.StaffRepository;
import com.example.cosmetic.service.StaffService;
import java.util.List;

public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepo;

    public StaffServiceImpl(StaffRepository staffRepo) {
        this.staffRepo = staffRepo;
    }

    @Override
    public List<Staff> getAllStaffs() {
        return staffRepo.findAll();
    }

    @Override
    public void addStaff(Staff staff) throws Exception {
        if (staff.getStaffCode().isEmpty() || staff.getFullName().isEmpty() || 
            staff.getUsername().isEmpty() || staff.getPassword().isEmpty()) {
            throw new Exception("Vui lòng điền đầy đủ thông tin!");
        }
        
        if (staffRepo.findByUsername(staff.getUsername()) != null) {
            throw new Exception("Tài khoản (Username) đã tồn tại!");
        }

        staffRepo.save(staff);
    }

    @Override
    public void updateStaff(Staff staff) throws Exception {
        if (staff.getStaffCode().isEmpty() || staff.getFullName().isEmpty() || staff.getUsername().isEmpty()) {
            throw new Exception("Vui lòng điền đầy đủ thông tin!");
        }
        staffRepo.update(staff);
    }

    @Override
    public void deleteStaff(Long id) throws Exception {
        staffRepo.delete(id);
    }
}