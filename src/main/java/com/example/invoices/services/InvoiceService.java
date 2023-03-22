package com.example.invoices.services;

import com.example.invoices.entities.Invoice;
import com.example.invoices.entities.Product;
import com.example.invoices.models.InvoiceFilterClientNameRequest;
import com.example.invoices.models.InvoiceFilterCreationDateRequest;
import com.example.invoices.models.InvoiceRequest;
import com.example.invoices.models.InvoiceResponse;
import com.example.invoices.repositories.InvoiceRepository;
import com.example.invoices.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ProductRepository productRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {

        Invoice createdInvoice = new Invoice();
        createdInvoice.setClientName(invoiceRequest.getClientName());
        createdInvoice.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
        createdInvoice = invoiceRepository.save(createdInvoice);
        List<Product> createdProducts = new ArrayList<>();

        for (String productName : invoiceRequest.getProducts()) {
            Product product = new Product();
            product.setInvoice(createdInvoice);
            product.setProductName(productName);
            product = productRepository.save(product);
            createdProducts.add(product);

        }
        return createInvoiceResponse(createdInvoice, createdProducts);
    }

    public InvoiceResponse getInvoice(int id) {

         Invoice invoice = invoiceRepository.getReferenceById(id);
         List<Product> products = getRelatedProductsById(id);
         return createInvoiceResponse(invoice, products);
    }

    public List<InvoiceResponse> getAllInvoices() {

        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();

        List<Invoice> invoices = invoiceRepository.findAll();
        for (Invoice invoice : invoices) {
            List<Product> products = getRelatedProductsById(invoice.getId());
            invoiceResponseList.add(createInvoiceResponse(invoice, products));

        }
       return invoiceResponseList;
    }

    public InvoiceResponse updateInvoice(InvoiceRequest invoiceRequest, int id) {

        Invoice loadedInvoice = invoiceRepository.getReferenceById(id);
        List<Product> loadedProducts = getRelatedProductsById(id);

        loadedInvoice.setClientName(invoiceRequest.getClientName());
        loadedInvoice.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
        loadedInvoice = invoiceRepository.save(loadedInvoice);
        productRepository.deleteAll(loadedProducts);

        List<Product> updatedProducts = new ArrayList<>();

        for (String productName : invoiceRequest.getProducts()) {
            Product product = new Product();
            product.setInvoice(loadedInvoice);
            product.setProductName(productName);
            product = productRepository.save(product);
            updatedProducts.add(product);
        }

        return createInvoiceResponse(loadedInvoice, updatedProducts);
    }

    public void deleteInvoice(int id) {

        List<Product> products = getRelatedProductsById(id);
        productRepository.deleteAll(products);
        invoiceRepository.deleteById(id);
    }

    public List<InvoiceResponse> filterInvoiceByClientName(InvoiceFilterClientNameRequest invoiceFilter) {

        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        List<Invoice> loadedInvoices = invoiceRepository.findByClientNameContaining(invoiceFilter.getClientName());

        for (Invoice loadedInvoice : loadedInvoices) {
            List<Product> products = getRelatedProductsById(loadedInvoice.getId());
            invoiceResponseList.add(createInvoiceResponse(loadedInvoice, products));
        }

        return invoiceResponseList;
    }

    public List<InvoiceResponse> filterInvoiceByCreationDate(InvoiceFilterCreationDateRequest invoiceFilter) {

        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        List<Invoice> loadedInvoices = invoiceRepository.findAll();

        for (Invoice loadedInvoice : loadedInvoices) {
            if (loadedInvoice.getCreatedAt().isAfter(invoiceFilter.getCreationDate())) {

                List<Product> products = getRelatedProductsById(loadedInvoice.getId());
                invoiceResponseList.add(createInvoiceResponse(loadedInvoice, products));
            }
        }
        return invoiceResponseList;
    }

    public InvoiceResponse filterInvoiceByLastModified() {

        Invoice invoice = invoiceRepository.findFirstByOrderByUpdatedAtDesc();

        return createInvoiceResponse(invoice,getRelatedProductsById(invoice.getId()));

    }

    private List<Product> getRelatedProductsById(int id) {

        return productRepository.findAllProductsByInvoiceId(id);
    }

    private InvoiceResponse createInvoiceResponse(Invoice invoice, List<Product> products) {
        InvoiceResponse response = new InvoiceResponse();

        response.setId(invoice.getId());
        response.setClientName(invoice.getClientName());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        List<String> createdProductsName = new ArrayList<>();
        for (Product product : products) {
            createdProductsName.add(product.getProductName());
        }
        response.setProducts(createdProductsName);
        response.setCreatedAt(invoice.getCreatedAt());
        response.setUpdatedAt(invoice.getUpdatedAt());

        return response;
    }

}
