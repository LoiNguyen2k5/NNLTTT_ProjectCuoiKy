package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Invoice;
import java.util.List;

public interface InvoiceRepository {
    // Lưu hóa đơn và thực hiện trừ kho (Transaction)
    void createInvoice(Invoice invoice) throws Exception;

    // Tìm kiếm hóa đơn theo ID (để in lại bill nếu cần)
    Invoice findById(Long id);

    // Lấy danh sách hóa đơn để làm báo cáo
    List<Invoice> findAll();
}