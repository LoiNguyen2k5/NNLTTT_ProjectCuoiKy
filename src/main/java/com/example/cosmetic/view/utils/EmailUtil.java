package com.example.cosmetic.view.utils;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class EmailUtil {
    
    // ⚠️ QUAN TRỌNG: ĐIỀN THÔNG TIN GMAIL CỦA BẠN VÀO ĐÂY
    private static final String MY_EMAIL = "tiemnet.coaching.y3@gmail.com"; 
    private static final String MY_APP_PASSWORD = "ggyg rjuu yelg vaea"; // Đọc kỹ Bước 4 để biết cách lấy mã này

    public static void sendInvoiceAsync(String toEmail, String customerName, String pdfFilePath) {
        // Chạy tiến trình gửi mail ngầm để không làm đơ giao diện Swing
        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MY_EMAIL, MY_APP_PASSWORD);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(MY_EMAIL, "Cửa Hàng Mỹ Phẩm"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject("Hóa Đơn Mua Hàng - Cửa Hàng Mỹ Phẩm");

                // Phần 1: Nội dung chữ (Body)
                BodyPart messageBodyPart = new MimeBodyPart();
                String htmlContent = "<h3>Kính chào quý khách " + customerName + ",</h3>"
                        + "<p>Cảm ơn quý khách đã mua sắm tại Cửa Hàng Mỹ Phẩm của chúng tôi.</p>"
                        + "<p>Chúng tôi xin gửi đính kèm hóa đơn mua hàng của quý khách trong email này.</p>"
                        + "<br><p>Trân trọng,</p><p><b>Đội ngũ Chăm sóc khách hàng</b></p>";
                messageBodyPart.setContent(htmlContent, "text/html; charset=UTF-8");

                // Phần 2: Đính kèm file PDF hóa đơn
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                MimeBodyPart attachmentPart = new MimeBodyPart();
                File f = new File(pdfFilePath);
                attachmentPart.attachFile(f);
                multipart.addBodyPart(attachmentPart);

                message.setContent(multipart);
                Transport.send(message);
                
                System.out.println("Đã gửi email thành công tới: " + toEmail);

            } catch (Exception e) {
                System.out.println("Lỗi gửi email: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }
}