package com.example.invoices.controllers;

import com.example.invoices.models.InvoiceFilterClientNameRequest;
import com.example.invoices.models.InvoiceFilterCreationDateRequest;
import com.example.invoices.models.InvoiceRequest;
import com.example.invoices.models.InvoiceResponse;
import com.example.invoices.repositories.InvoiceRepository;
import com.example.invoices.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/invoice")
public class InvoiceController extends RuntimeException {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    InvoiceService invoiceService;

    @PostMapping(value = "/create")
    public InvoiceResponse createInvoice(@RequestBody InvoiceRequest invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @GetMapping(value = "/{id}")
    public InvoiceResponse readInvoice(@PathVariable Integer id) {

        return invoiceService.getInvoice(id);
    }

    @GetMapping
    public List<InvoiceResponse> readAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @PutMapping(value = "/update/{id}")
    public InvoiceResponse updateInvoice(@RequestBody InvoiceRequest invoice, @PathVariable Integer id) {
        return invoiceService.updateInvoice(invoice, id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteInvoice(@PathVariable int id) {
        invoiceService.deleteInvoice(id);
    }
    @GetMapping(value = "/filter/clientName")
    public List<InvoiceResponse> filterInvoiceByClientName(@RequestBody InvoiceFilterClientNameRequest invoiceFilter) {
        return invoiceService.filterInvoiceByClientName(invoiceFilter);
    }

    @GetMapping(value = "/filter/creationDate")
    public List<InvoiceResponse> filterInvoiceByCreationDate(@RequestBody InvoiceFilterCreationDateRequest invoiceFilter) {
        return invoiceService.filterInvoiceByCreationDate(invoiceFilter);
    }

    @GetMapping(value = "/filter/lastModified")
    public InvoiceResponse filterInvoiceByLastModified() {
        return invoiceService.filterInvoiceByLastModified();
    }
}

