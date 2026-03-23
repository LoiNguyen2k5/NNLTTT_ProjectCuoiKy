package com.example.cosmetic.view.auth;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Đăng nhập - Cosmetics Management");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("  Tài khoản:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("  Mật khẩu:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        add(new JLabel("")); // Cột trống để đẩy nút Login sang phải
        btnLogin = new JButton("Đăng nhập");
        add(btnLogin);
    }

    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JButton getBtnLogin() { return btnLogin; }
}