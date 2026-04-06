
package com.example.cosmetic.view.utils;
import java.io.File;

public class DatabaseBackupUtil {

    private static String getMysqlDumpPath() {
        String[] commonPaths = {
            "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Workbench 8.0\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Workbench 8.0 CE\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Server 8.1\\bin\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Server 8.2\\bin\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Server 8.3\\bin\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Server 8.4\\bin\\mysqldump.exe",
            "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump.exe",
            "C:\\xampp\\mysql\\bin\\mysqldump.exe"
        };
        for (String path : commonPaths) {
            if (new File(path).exists()) {
                return path;
            }
        }
        return "mysqldump"; // fallback to PATH
    }
    
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
            String dumpPath = getMysqlDumpPath();
            
            // Xây dựng câu lệnh chạy mysqldump
            if (dbPass == null || dbPass.trim().isEmpty()) {
                pb = new ProcessBuilder(dumpPath, "-u", dbUser, dbName, "-r", savePath);
            } else {
                pb = new ProcessBuilder(dumpPath, "-u", dbUser, "-p" + dbPass, dbName, "-r", savePath);
            }

            // Gộp luồng lỗi vào output để xử lý chung
            pb.redirectErrorStream(true);
            
            // Thực thi lệnh ẩn dưới background
            Process process = pb.start();
            
            // Phải đọc output để tránh bị treo (deadlock) nếu mysqldump sinh ra quá nhiều log
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Có thể log ra console
                    System.out.println("mysqldump: " + line);
                }
            }
            
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
