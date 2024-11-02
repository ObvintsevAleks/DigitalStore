package com.personal.DigitalStore.controllers;

import com.personal.DigitalStore.dto.InvoiceLineDTO;
import com.personal.DigitalStore.dto.InvoiceLineSaveDTO;
import com.personal.DigitalStore.services.InvoiceLineService;
import com.personal.DigitalStore.utils.swagger.ApiCreate;
import com.personal.DigitalStore.utils.swagger.ApiDelete;
import com.personal.DigitalStore.utils.swagger.ApiGet;
import com.personal.DigitalStore.utils.swagger.ApiUpdate;
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
@Tag(name = "invoice-line-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/invoice-lines")
public class InvoiceLineController {

    private final InvoiceLineService service;

    @ApiGet
    @Operation(summary = "Получить сформированный заказ по идентификатору")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceLineDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceLine(@PathVariable("id") UUID invoiceLineId) {
        log.info("Request to display a artist with ID {}", invoiceLineId);
        return new ResponseEntity<>(service.getInvoiceLineById(invoiceLineId), HttpStatus.OK);
    }

    @ApiUpdate
    @Operation(summary = "Обновить сформированный заказ")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceLineDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateInvoiceLine(@RequestBody InvoiceLineDTO invoiceDTO) {
        return new ResponseEntity<>(service.updateInvoiceLine(invoiceDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить сформированный заказ")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvoiceLine(@PathVariable("id") UUID invoiceLineId) {
        return new ResponseEntity<>(service.deleteInvoiceLineById(invoiceLineId), HttpStatus.NO_CONTENT);
    }

    @ApiCreate
    @Operation(summary = "Создать сформированный заказ")
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = InvoiceLineDTO.class)))
    @PostMapping
    public ResponseEntity<?> createInvoiceLine(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = InvoiceLineSaveDTO.class))
            @Valid @RequestBody InvoiceLineSaveDTO invoiceLineSaveDTO) {
        return new ResponseEntity<>(service.createInvoiceLine(invoiceLineSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить список сформированных заказов по идентификатору аудиозаписи")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceLineDTO.class)))
    @GetMapping("/invoice-lines-by-track/{id}")
    public ResponseEntity<?> getInvoiceLinesByTrackId(@PathVariable("id") UUID trackId) {
        log.info("Request to display a artist with ID {}", trackId);
        return new ResponseEntity<>(service.getInvoiceLineByTrackId(trackId), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить список сформированных заказов по идентификатору заказа")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = InvoiceLineDTO.class)))
    @GetMapping("/invoice-line-by-invoice/{id}")
    public ResponseEntity<?> getInvoiceLinesByInvoiceId(@PathVariable("id") UUID invoiceId) {
        log.info("Request to display a artist with ID {}", invoiceId);
        return new ResponseEntity<>(service.getInvoiceLineByInvoiceId(invoiceId), HttpStatus.OK);
    }

}
