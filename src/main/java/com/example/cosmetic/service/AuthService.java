package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Staff;

public interface AuthService {
    Staff login(String username, String password) throws Exception;
}