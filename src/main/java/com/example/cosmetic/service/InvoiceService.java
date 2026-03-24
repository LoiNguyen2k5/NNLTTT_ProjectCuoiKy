package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Invoice;
import java.util.List;

public interface InvoiceService {
    void processCheckout(Invoice invoice) throws Exception;
    List<Invoice> getAllInvoices();
}