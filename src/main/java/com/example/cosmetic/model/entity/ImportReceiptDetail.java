package com.example.cosmetic.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "import_receipt_details")
public class ImportReceiptDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private ImportReceipt receipt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(name = "import_price", nullable = false)
    private BigDecimal importPrice;

    public ImportReceiptDetail() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ImportReceipt getReceipt() { return receipt; }
    public void setReceipt(ImportReceipt receipt) { this.receipt = receipt; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getImportPrice() { return importPrice; }
    public void setImportPrice(BigDecimal importPrice) { this.importPrice = importPrice; }
}