package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Invoice;
import com.example.cosmetic.repository.InvoiceRepository;
import com.example.cosmetic.service.InvoiceService;

public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void processCheckout(Invoice invoice) throws Exception {
        // Có thể thêm logic kiểm tra tổng tiền, mã giảm giá ở đây
        if (invoice.getDetails() == null || invoice.getDetails().isEmpty()) {
            throw new Exception("Giỏ hàng đang trống!");
        }
        invoiceRepository.createInvoice(invoice);
    }
}