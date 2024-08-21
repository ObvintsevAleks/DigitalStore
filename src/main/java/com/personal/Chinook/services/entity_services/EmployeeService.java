package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.EmployeeDTO;
import com.personal.Chinook.DTO.EmployeeSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.EmployeeMapper;
import com.personal.Chinook.models.Employee;
import com.personal.Chinook.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional
    public EmployeeDTO createEmployee(EmployeeSaveDTO employeeSaveDTO) {
        Employee employee = employeeMapper.toEmployee(employeeSaveDTO);
        employeeRepository.save(employee);
        return employeeMapper.toEmployeeDTO(employee);
    }
    
    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(UUID id) throws NotFoundInDBException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        return employeeMapper.toEmployeeDTO(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws NotFoundInDBException {
        Employee employeeEntity = employeeRepository.findById(employeeDTO.getId()).orElseThrow(() -> new NotFoundInDBException(""));
        if (employeeMapper.toEmployeeDTO(employeeEntity).equals(employeeDTO)) {
            return employeeMapper.toEmployeeDTO(employeeEntity);
        }
        employeeMapper.updateEmployee(employeeEntity, employeeDTO);
        employeeRepository.save(employeeEntity);
        return employeeMapper.toEmployeeDTO(employeeEntity);
    }

    @Transactional
    public EmployeeDTO deleteEmployeeById(UUID id) throws NotFoundInDBException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("asrd"));
        UUID employeeId = employee.getId();
        employeeRepository.deleteById(employeeId);
        return employeeMapper.toEmployeeDTO(employee);
    }


    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeListByFirstname(String firstname) throws NotFoundInDBException {
        List<Employee> employees = employeeRepository.searchByFirstname(firstname);
        if (employees.isEmpty()) {
            throw new NotFoundInDBException("2");
        }
        return employeeMapper.toEmployeeDTOs(employees);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeListByLastname(String lastname) throws NotFoundInDBException {
        List<Employee> employees = employeeRepository.searchByLastname(lastname);
        if (employees.isEmpty()) {
            throw new NotFoundInDBException("2");
        }
        return employeeMapper.toEmployeeDTOs(employees);
    }
    
}
