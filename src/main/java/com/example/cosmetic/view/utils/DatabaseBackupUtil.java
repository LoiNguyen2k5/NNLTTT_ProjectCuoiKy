package com.example.cosmetic.view.utils;
import java.io.File;

public class DatabaseBackupUtil {
    
    /**
     * Hàm thực thi lệnh mysqldump để sao lưu database
     * @param dbUser Tên đăng nhập MySQL (thường là root)
     * @param dbPass Mật khẩu MySQL (nếu không có thì để trống "")
     * @param dbName Tên database cần sao lưu (cosmetics_management)
     * @param savePath Đường dẫn file .sql sẽ lưu ra
     * @return true nếu thành công, false nếu thất bại
     */
    public static boolean backup(String dbUser, String dbPass, String dbName, String savePath) {
        try {
            ProcessBuilder pb;
            // Xây dựng câu lệnh chạy mysqldump
            if (dbPass == null || dbPass.trim().isEmpty()) {
                pb = new ProcessBuilder("mysqldump", "-u", dbUser, dbName, "-r", savePath);
            } else {
                pb = new ProcessBuilder("mysqldump", "-u", dbUser, "-p" + dbPass, dbName, "-r", savePath);
            }

            // Thực thi lệnh ẩn dưới background
            Process process = pb.start();
            
            // Chờ lệnh chạy xong
            int processComplete = process.waitFor();
            
            // Nếu trả về 0 nghĩa là chạy thành công không có lỗi
            return processComplete == 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}