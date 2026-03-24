package com.example.cosmetic.service;
import com.example.cosmetic.model.entity.ImportReceipt;
import java.util.List;

public interface ImportReceiptService {
    void processImport(ImportReceipt receipt) throws Exception;
    List<ImportReceipt> getAllReceipts();
}