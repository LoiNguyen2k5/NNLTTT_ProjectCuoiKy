package com.example.cosmetic;

import com.example.cosmetic.controller.AuthController;
import com.example.cosmetic.repository.impl.StaffRepositoryImpl;
import com.example.cosmetic.service.impl.AuthServiceImpl;
import com.example.cosmetic.view.auth.LoginFrame;

import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Khởi tạo các thành phần theo đúng chuẩn Dependency Inversion
            StaffRepositoryImpl repo = new StaffRepositoryImpl();
            AuthServiceImpl service = new AuthServiceImpl(repo);
            LoginFrame view = new LoginFrame();
            
            // Gắn vào Controller
            new AuthController(service, view);
            
            // Hiển thị giao diện
            view.setVisible(true);
        });
    }
}