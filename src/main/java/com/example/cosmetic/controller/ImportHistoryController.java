package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.ImportReceipt;
import com.example.cosmetic.service.ImportReceiptService;
import com.example.cosmetic.view.invoice.ImportHistoryPanel;

public class ImportHistoryController {
    public ImportHistoryController(ImportReceiptService service, ImportHistoryPanel view) {
        view.getBtnRefresh().addActionListener(e -> {
            view.getTableModel().setRowCount(0);
            service.getAllReceipts().forEach(r -> view.getTableModel().addRow(new Object[]{
                r.getId(), r.getReceiptCode(), r.getSupplier().getName(), r.getStaff().getFullName(), r.getImportDate(), r.getTotalAmount()
            }));
        });
        view.getBtnRefresh().doClick(); // Tự động load lần đầu
    }
}