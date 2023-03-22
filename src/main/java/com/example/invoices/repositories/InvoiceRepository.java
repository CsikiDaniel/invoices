package com.example.invoices.repositories;


import com.example.invoices.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    Invoice findFirstByOrderByUpdatedAtDesc();
    List<Invoice> findByClientNameContaining(String clientName);
}
