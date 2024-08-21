package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.ArtistSaveDTO;
import com.personal.Chinook.DTO.EmployeeDTO;
import com.personal.Chinook.DTO.EmployeeSaveDTO;
import com.personal.Chinook.services.entity_services.ArtistService;
import com.personal.Chinook.services.entity_services.EmployeeService;
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
@Tag(name = "artists")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;

    @ApiGet
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable("id") UUID employeeId) {
        log.info("Request to display a artist with ID {}", employeeId);
        return service.getEmployeeById(employeeId);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateEmployeeByDto(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(service.updateEmployee(employeeDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") UUID employeeId) {
        return new ResponseEntity<>(service.deleteEmployeeById(employeeId), HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ApiCreate
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeSaveDTO employeeSaveDTO) {
        return new ResponseEntity<>(service.createEmployee(employeeSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @GetMapping("/{byFirstname}")
    public ResponseEntity<?> getEmployeeByLastname(@PathVariable("byFirstname") String firstName) {
        return new ResponseEntity<>(service.getEmployeeListByFirstname(firstName), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/{byLastname}")
    public ResponseEntity<?> getEmployeeByFirstname(@PathVariable("byLastname") String lastname) {
        return new ResponseEntity<>(service.getEmployeeListByLastname(lastname), HttpStatus.OK);
    }



}
