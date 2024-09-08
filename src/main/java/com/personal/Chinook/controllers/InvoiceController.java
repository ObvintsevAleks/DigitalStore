package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.InvoiceDTO;
import com.personal.Chinook.DTO.InvoiceSaveDTO;
import com.personal.Chinook.services.entity_services.InvoiceService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Tag(name = "invoice-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService service;

    @ApiGet
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable("id") UUID invoiceId) {
        log.info("Request to display a artist with ID {}", invoiceId);
        return new ResponseEntity<>(service.getInvoiceById(invoiceId), HttpStatus.OK);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return new ResponseEntity<>(service.updateInvoice(invoiceDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable("id") UUID invoiceId) {
        return new ResponseEntity<>(service.deleteInvoiceById(invoiceId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceSaveDTO invoiceSaveDTO) {
        return new ResponseEntity<>(service.createInvoice(invoiceSaveDTO), HttpStatus.CREATED);
    }

}
