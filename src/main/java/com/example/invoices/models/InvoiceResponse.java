package com.example.invoices.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InvoiceResponse {
    private int id;
    private String clientName;
    private String invoiceNumber;
    private List<String> products;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
