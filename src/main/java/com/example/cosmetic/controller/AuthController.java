package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.service.AuthService;
import com.example.cosmetic.view.auth.LoginFrame;
import com.example.cosmetic.view.main.MainFrame;

import javax.swing.*;

public class AuthController {
    private final AuthService authService;
    private final LoginFrame loginFrame;

    public AuthController(AuthService authService, LoginFrame loginFrame) {
        this.authService = authService;
        this.loginFrame = loginFrame;

        // Bắt sự kiện khi người dùng bấm nút Đăng nhập
        this.loginFrame.getBtnLogin().addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = loginFrame.getTxtUsername().getText();
        String password = new String(loginFrame.getTxtPassword().getPassword());

        try {
            Staff loggedInStaff = authService.login(username, password);
            JOptionPane.showMessageDialog(loginFrame, "Đăng nhập thành công! Xin chào " + loggedInStaff.getFullName());
            
            // Đóng form Login và mở MainFrame
            loginFrame.dispose();
            MainFrame mainFrame = new MainFrame(loggedInStaff);
            mainFrame.setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(loginFrame, ex.getMessage(), "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }
}