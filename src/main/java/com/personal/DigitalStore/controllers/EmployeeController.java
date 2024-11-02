package com.personal.DigitalStore.controllers;

import com.personal.DigitalStore.dto.EmployeeDTO;
import com.personal.DigitalStore.dto.EmployeeSaveDTO;
import com.personal.DigitalStore.services.EmployeeService;
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
@Tag(name = "employee-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    @ApiGet
    @Operation(summary = "Получить работника по идентификатору")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id") UUID employeeId) {
        log.info("Request to display a artist with ID {}", employeeId);
        return new ResponseEntity<>(service.getEmployeeById(employeeId), HttpStatus.OK);
    }

    @ApiUpdate
    @Operation(summary = "Обновить работника")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(service.updateEmployee(employeeDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить работника")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") UUID employeeId) {
        return new ResponseEntity<>(service.deleteEmployeeById(employeeId), HttpStatus.NO_CONTENT);
    }

    @ApiCreate
    @Operation(summary = "Создать работника")
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = EmployeeDTO.class)))
    @PostMapping
    public ResponseEntity<?> createEmployee(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = EmployeeSaveDTO.class))
            @Valid @RequestBody EmployeeSaveDTO employeeSaveDTO) {
        return new ResponseEntity<>(service.createEmployee(employeeSaveDTO), HttpStatus.CREATED);
    }

    @ApiGet
    @Operation(summary = "Получить список работников по имени")
    @GetMapping("/firstname/{name}")
    public ResponseEntity<?> getEmployeeByLastname(@PathVariable("name") String firstName) {
        return new ResponseEntity<>(service.getEmployeeListByFirstname(firstName), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить список работников по фамилии")
    @GetMapping("/lastname/{name}")
    public ResponseEntity<?> getEmployeeByFirstname(@PathVariable("name") String lastname) {
        return new ResponseEntity<>(service.getEmployeeListByLastname(lastname), HttpStatus.OK);
    }

}
