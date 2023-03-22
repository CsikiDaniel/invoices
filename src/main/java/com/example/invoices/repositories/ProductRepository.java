package com.example.invoices.repositories;


import com.example.invoices.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllProductsByInvoiceId(int id);
}
