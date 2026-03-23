package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Invoice;

public interface InvoiceService {
    void processCheckout(Invoice invoice) throws Exception;
}