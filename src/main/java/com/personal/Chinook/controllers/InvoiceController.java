package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.*;
import com.personal.Chinook.services.entity_services.InvoiceService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(summary = "Получить заказ по идентификатору", description = "Получить заказ по идентификатору", tags = {"invoice-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable("id") UUID invoiceId) {
        log.info("Request to display a artist with ID {}", invoiceId);
        return new ResponseEntity<>(service.getInvoiceById(invoiceId), HttpStatus.OK);
    }

    @ApiUpdate
    @Operation(summary = "Обновить заказ", description = "Обновить заказ", tags = {"invoice-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return new ResponseEntity<>(service.updateInvoice(invoiceDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить заказ", description = "Удалить заказ", tags = {"invoice-controller"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable("id") UUID invoiceId) {
        return new ResponseEntity<>(service.deleteInvoiceById(invoiceId), HttpStatus.NO_CONTENT);
    }

    @ApiCreate
    @Operation(summary = "Создать заказ", description = "Создать заказ", tags = {"invoice-controller"})
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
    @PostMapping
    public ResponseEntity<?> createInvoice(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = InvoiceSaveDTO.class))
            @Valid @RequestBody InvoiceSaveDTO invoiceSaveDTO) {
        return new ResponseEntity<>(service.createInvoice(invoiceSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить заказ по идентификатору клиента", description = "Получить заказ по идентификатору клиента", tags = {"invoice-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
    @GetMapping("/invoices-by-customer/{id}")
    public ResponseEntity<?> getInvoicesByCustomerId(@PathVariable("id") UUID customerId) {
        log.info("Request to display a artist with ID {}", customerId);
        return new ResponseEntity<>(service.getInvoiceByCustomerId(customerId), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить заказ по идентификатору сотрудника", description = "Получить заказ по идентификатору сотрудника", tags = {"invoice-controller"})
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
    @GetMapping("/invoices-by-employee/{id}")
    public ResponseEntity<?> getInvoicesByEmployeeId(@PathVariable("id") UUID employeeId) {
        log.info("Request to display a artist with ID {}", employeeId);
        return new ResponseEntity<>(service.getInvoiceByEmployeeId(employeeId), HttpStatus.OK);
    }

}
