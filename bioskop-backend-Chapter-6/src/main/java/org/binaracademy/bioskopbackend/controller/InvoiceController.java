package org.binaracademy.bioskopbackend.controller;

import net.sf.jasperreports.engine.JRException;
import org.binaracademy.bioskopbackend.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping(value = "/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping(value = "/generate-invoice", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity generateInvoice(@RequestHeader("username") String username) throws JRException, FileNotFoundException {
        return ResponseEntity.ok()
                .body(invoiceService.generateInvoice(username));
    }
}
