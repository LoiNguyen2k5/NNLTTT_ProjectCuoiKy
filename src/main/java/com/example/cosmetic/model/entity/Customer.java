package com.example.cosmetic.model.entity;

import com.example.cosmetic.model.enums.CustomerGender;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerGender gender;

    // Cấp giá trị mặc định và chặn null từ phía Java
    @Column(name = "tier", nullable = false)
    private String tier = "MEMBER";

    @Column(name = "points", nullable = false)
    private int points = 0;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    public Customer() {
        // Tự động phát sinh mã KH
        this.customerCode = "KH" + System.currentTimeMillis(); 
    }

    // ==========================================
    // GETTERS AND SETTERS (Đã bổ sung đầy đủ)
    // ==========================================
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public CustomerGender getGender() { return gender; }
    public void setGender(CustomerGender gender) { this.gender = gender; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() { return fullName + " (" + phoneNumber + ")"; }
}