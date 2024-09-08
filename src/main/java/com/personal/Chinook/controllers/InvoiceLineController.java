package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.InvoiceLineDTO;
import com.personal.Chinook.DTO.InvoiceLineSaveDTO;
import com.personal.Chinook.services.entity_services.InvoiceLineService;
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
@Tag(name = "invoice-line-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/invoice-lines")
public class InvoiceLineController {

    private final InvoiceLineService service;

    @ApiGet
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceLine(@PathVariable("id") UUID invoiceLineId) {
        log.info("Request to display a artist with ID {}", invoiceLineId);
        return new ResponseEntity<>(service.getInvoiceLineById(invoiceLineId), HttpStatus.OK);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateInvoiceLine(@RequestBody InvoiceLineDTO invoiceDTO) {
        return new ResponseEntity<>(service.updateInvoiceLine(invoiceDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoiceLine(@PathVariable("id") UUID invoiceLineId) {
        return new ResponseEntity<>(service.deleteInvoiceLineById(invoiceLineId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createInvoiceLine(@RequestBody InvoiceLineSaveDTO invoiceLineSaveDTO) {
        return new ResponseEntity<>(service.createInvoiceLine(invoiceLineSaveDTO), HttpStatus.CREATED);
    }

}
