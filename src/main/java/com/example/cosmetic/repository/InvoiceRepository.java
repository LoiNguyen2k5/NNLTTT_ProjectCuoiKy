package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Invoice;
import java.util.List;

public interface InvoiceRepository {
    List<Invoice> findAll();
}