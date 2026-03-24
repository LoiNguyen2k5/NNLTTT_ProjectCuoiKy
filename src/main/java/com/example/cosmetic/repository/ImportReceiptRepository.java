package com.example.cosmetic.repository;
import com.example.cosmetic.model.entity.ImportReceipt;
import java.util.List;

public interface ImportReceiptRepository {
    List<ImportReceipt> findAll();
}