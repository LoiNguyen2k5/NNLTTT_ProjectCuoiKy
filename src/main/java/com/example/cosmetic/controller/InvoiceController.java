package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Invoice;
import com.example.cosmetic.service.InvoiceService;
import com.example.cosmetic.view.invoice.InvoiceManagementPanel;
import java.util.List;

public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceManagementPanel view;

    public InvoiceController(InvoiceService service, InvoiceManagementPanel view) {
        this.invoiceService = service;
        this.view = view;
        loadTable();
        initEvents();
    }

    private void loadTable() {
        view.getTableModel().setRowCount(0);
        try {
            List<Invoice> list = invoiceService.getAllInvoices();
            if (list != null) {
                list.forEach(inv -> {
                    String customerName = inv.getCustomer() != null ? inv.getCustomer().getFullName() : "Khách lẻ";
                    String staffName = inv.getStaff() != null ? inv.getStaff().getFullName() : "";
                    view.getTableModel().addRow(new Object[]{
                        inv.getId(), inv.getInvoiceCode(), customerName, staffName, inv.getInvoiceDate(), inv.getTotalAmount()
                    });
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void initEvents() {
        view.getBtnRefresh().addActionListener(e -> loadTable());
    }
}