package com.example.invoices.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceFilterCreationDateRequest {

    private LocalDateTime creationDate;
}
