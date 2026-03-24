package com.example.cosmetic.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_code", unique = true, nullable = false)
    private String invoiceCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "invoice_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    // Quan hệ 1-Nhiều với bảng chi tiết hóa đơn
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceDetail> details;

    public Invoice() {}

    // =========================================
    // GETTERS AND SETTERS (Dùng để sửa lỗi đỏ)
    // =========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInvoiceCode() { return invoiceCode; }
    public void setInvoiceCode(String invoiceCode) { this.invoiceCode = invoiceCode; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Staff getStaff() { return staff; }
    public void setStaff(Staff staff) { this.staff = staff; }

    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public List<InvoiceDetail> getDetails() { return details; }
    public void setDetails(List<InvoiceDetail> details) { this.details = details; }
}