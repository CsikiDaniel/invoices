package com.example.invoices.models;

import lombok.Data;

import java.util.List;
@Data
public class InvoiceRequest {

    private String clientName;

    private String invoiceNumber;

    private List<String> products;

}
