package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.CustomerDTO;
import com.personal.Chinook.DTO.CustomerSaveDTO;
import com.personal.Chinook.services.entity_services.CustomerService;
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
@Tag(name = "customers")
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    @ApiGet
    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") UUID customerId) {
        log.info("Request to display a artist with ID {}", customerId);
        return service.getCustomerById(customerId);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(service.updateCustomer(customerDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") UUID customerId) {
        return new ResponseEntity<>(service.deleteCustomerById(customerId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createCustomer(@RequestBody CustomerSaveDTO customerSaveDTO) {
        return new ResponseEntity<>(service.createCustomer(customerSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/{firstname}")
    public ResponseEntity<?> getCustomerByLastname(@PathVariable("firstname") String firstName) {
        return new ResponseEntity<>(service.getCustomerListByFirstname(firstName), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/{lastname}")
    public ResponseEntity<?> getCustomerByFirstname(@PathVariable("lastname") String lastname) {
        return new ResponseEntity<>(service.getCustomerListByLastname(lastname), HttpStatus.OK);
    }

}
