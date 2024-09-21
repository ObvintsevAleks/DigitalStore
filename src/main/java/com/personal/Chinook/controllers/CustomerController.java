package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.CustomerDTO;
import com.personal.Chinook.DTO.CustomerSaveDTO;
import com.personal.Chinook.DTO.EmployeeDTO;
import com.personal.Chinook.DTO.EmployeeSaveDTO;
import com.personal.Chinook.services.entity_services.CustomerService;
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
@Tag(name = "customer-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    @ApiGet
    @Operation(summary = "Получить клиента по идентификатору", description = "Получить клиента по идентификатору")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") UUID customerId) {
        log.info("Request to display a artist with ID {}", customerId);
        return new ResponseEntity<>(service.getCustomerById(customerId), HttpStatus.OK);
    }

    @ApiUpdate
    @Operation(summary = "Обновить клиента", description = "Обновить клиента")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(service.updateCustomer(customerDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить клиента", description = "Удалить клиента")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") UUID customerId) {
        return new ResponseEntity<>(service.deleteCustomerById(customerId), HttpStatus.NO_CONTENT);
    }

    @ApiCreate
    @Operation(summary = "Создать клиента", description = "Создать клиента")
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    @PostMapping
    public ResponseEntity<?> createCustomer(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = CustomerSaveDTO.class))
            @Valid @RequestBody CustomerSaveDTO customerSaveDTO) {
        return new ResponseEntity<>(service.createCustomer(customerSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить список клиентов по имени", description = "Получить список клиентов по имени")
    @GetMapping("/firstname/{name}")
    public ResponseEntity<?> getCustomerByFirstname(@PathVariable("name") String firstName) {
        return new ResponseEntity<>(service.getCustomerListByFirstname(firstName), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить список клиентов по фамилии", description = "Получить список клиентов по фамилии")
    @GetMapping("/lastname/{name}")
    public ResponseEntity<?> getCustomerByLastname(@PathVariable("name") String lastname) {
        return new ResponseEntity<>(service.getCustomerListByLastname(lastname), HttpStatus.OK);
    }

}
