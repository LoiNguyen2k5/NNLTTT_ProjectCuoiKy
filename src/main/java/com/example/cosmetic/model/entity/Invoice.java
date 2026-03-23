package com.example.cosmetic.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_date")
    private LocalDateTime invoiceDate = LocalDateTime.now();

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // Nếu bán vãng lai thì null cũng được

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff; // Người lập hóa đơn

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceDetail> details;

    // Getters/Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getInvoiceDate() { return invoiceDate; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Customer getCustomer() { return customer; }
    public void setStaff(Staff staff) { this.staff = staff; }
    public Staff getStaff() { return staff; }
    public List<InvoiceDetail> getDetails() { return details; }
    public void setDetails(List<InvoiceDetail> details) { this.details = details; }
}