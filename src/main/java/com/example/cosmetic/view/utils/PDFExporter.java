package com.example.cosmetic.view.utils;

import com.example.cosmetic.model.entity.Invoice;
import com.example.cosmetic.model.entity.InvoiceDetail;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PDFExporter {
    public static void exportInvoice(Invoice invoice, String destPath) {
        try {
            // Tạo khổ giấy A5 (chuẩn in bill)
            Document document = new Document(PageSize.A5);
            PdfWriter.getInstance(document, new FileOutputStream(destPath));
            document.open();

            // Cấu hình font Arial có sẵn của Windows để hỗ trợ Tiếng Việt có dấu
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            try {
                BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontTitle = new Font(bf, 18, Font.BOLD);
                fontNormal = new Font(bf, 12, Font.NORMAL);
            } catch (Exception e) {
                System.out.println("Cảnh báo: Không load được font tiếng Việt.");
            }

            // 1. Tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("---------------------------------------------------------", fontNormal));

            // 2. Thông tin chung
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            document.add(new Paragraph("Mã HĐ: " + invoice.getInvoiceCode(), fontNormal));
            document.add(new Paragraph("Ngày lập: " + sdf.format(invoice.getInvoiceDate()), fontNormal));
            String cusName = invoice.getCustomer() != null ? invoice.getCustomer().getFullName() : "Khách lẻ";
            document.add(new Paragraph("Khách hàng: " + cusName, fontNormal));
            document.add(new Paragraph("Nhân viên: " + invoice.getStaff().getFullName(), fontNormal));
            document.add(new Paragraph(" "));

            // 3. Bảng sản phẩm
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1f, 2.5f, 2.5f}); // Tỷ lệ độ rộng các cột

            String[] headers = {"Tên SP", "SL", "Đơn giá", "Thành tiền"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, new Font(fontTitle.getBaseFont(), 12, Font.BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            DecimalFormat df = new DecimalFormat("#,### đ");
            for (InvoiceDetail d : invoice.getDetails()) {
                table.addCell(new Phrase(d.getProduct().getName(), fontNormal));
                
                PdfPCell cellQty = new PdfPCell(new Phrase(String.valueOf(d.getQuantity()), fontNormal));
                cellQty.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellQty);
                
                PdfPCell cellPrice = new PdfPCell(new Phrase(df.format(d.getPrice()), fontNormal));
                cellPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cellPrice);
                
                double total = d.getPrice().doubleValue() * d.getQuantity();
                PdfPCell cellTotal = new PdfPCell(new Phrase(df.format(total), fontNormal));
                cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cellTotal);
            }
            document.add(table);
            document.add(new Paragraph(" "));

            // 4. Tổng tiền & Lời cảm ơn
            Paragraph totalPara = new Paragraph("TỔNG CỘNG: " + df.format(invoice.getTotalAmount()), fontTitle);
            totalPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalPara);

            document.add(new Paragraph(" "));
            Paragraph thanks = new Paragraph("Cảm ơn quý khách và hẹn gặp lại!", fontNormal);
            thanks.setAlignment(Element.ALIGN_CENTER);
            document.add(thanks);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportStatisticsReport(long totalCust, long outOfStock, java.math.BigDecimal todayRev, java.math.BigDecimal totalRev, java.util.List<Object[]> topProducts, String destPath) {
        try {
            // Khổ giấy A4 chuẩn cho báo cáo
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(destPath));
            document.open();

            // Setup Font Tiếng Việt
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            try {
                BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontTitle = new Font(bf, 22, Font.BOLD);
                fontHeader = new Font(bf, 14, Font.BOLD);
                fontNormal = new Font(bf, 12, Font.NORMAL);
            } catch (Exception e) {}

            // 1. Tiêu đề Báo Cáo
            Paragraph title = new Paragraph("BÁO CÁO THỐNG KÊ KINH DOANH", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            SimpleDateFormat printDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Paragraph timeInfo = new Paragraph("Thời gian trích xuất: " + printDate.format(new java.util.Date()), fontNormal);
            timeInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(timeInfo);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("-------------------------------------------------------------------------------------------------------", fontNormal));
            document.add(new Paragraph(" "));

            // 2. Chỉ số tổng quan (Dashboard)
            DecimalFormat df = new DecimalFormat("#,### đ");
            document.add(new Paragraph("I. TỔNG QUAN HOẠT ĐỘNG:", fontHeader));
            document.add(new Paragraph("- Tổng số khách hàng trên hệ thống: " + totalCust + " người", fontNormal));
            document.add(new Paragraph("- Số sản phẩm đang cạn kho (Hết hàng): " + outOfStock + " sản phẩm", fontNormal));
            document.add(new Paragraph("- DOANH THU HÔM NAY: " + df.format(todayRev), fontHeader));
            document.add(new Paragraph("- TỔNG DOANH THU TOÀN THỜI GIAN: " + df.format(totalRev), fontHeader));
            document.add(new Paragraph(" "));

            // 3. Bảng Top Bán Chạy
            document.add(new Paragraph("II. TOP 10 SẢN PHẨM BÁN CHẠY NHẤT:", fontHeader));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4f, 1.5f, 2.5f, 2.5f}); 

            String[] headers = {"Tên Sản Phẩm", "Đã Bán", "Doanh Thu", "Ngày Bán Gần Nhất"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setPadding(8f);
                table.addCell(cell);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (Object[] row : topProducts) {
                // Tên SP
                PdfPCell cellName = new PdfPCell(new Phrase((String) row[0], fontNormal));
                cellName.setPadding(5f);
                table.addCell(cellName);
                
                // Số lượng
                PdfPCell cellQty = new PdfPCell(new Phrase(String.valueOf(row[1]), fontNormal));
                cellQty.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellQty.setPadding(5f);
                table.addCell(cellQty);
                
                // Doanh thu
                PdfPCell cellRev = new PdfPCell(new Phrase(df.format((java.math.BigDecimal) row[2]), fontNormal));
                cellRev.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellRev.setPadding(5f);
                table.addCell(cellRev);
                
                // Ngày bán gần nhất
                java.util.Date lastDate = (java.util.Date) row[3];
                String dateStr = lastDate != null ? sdf.format(lastDate) : "Chưa rõ";
                PdfPCell cellDate = new PdfPCell(new Phrase(dateStr, fontNormal));
                cellDate.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellDate.setPadding(5f);
                table.addCell(cellDate);
            }
            
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("------- Hết báo cáo -------", fontNormal));
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}