package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.EmployeeDTO;
import com.personal.DigitalStore.dto.EmployeeSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.EmployeeMapper;
import com.personal.DigitalStore.models.Employee;
import com.personal.DigitalStore.repositories.EmployeeRepository;
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
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден сотрудник по id = "+ id));
        return employeeMapper.toEmployeeDTO(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws NotFoundInDBException {
        Employee employeeEntity = employeeRepository.findById(employeeDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден сотрудник по id = "+ employeeDTO.getId()));
        if (employeeMapper.toEmployeeDTO(employeeEntity).equals(employeeDTO)) {
            return employeeMapper.toEmployeeDTO(employeeEntity);
        }
        employeeMapper.updateEmployee(employeeEntity, employeeDTO);
        employeeRepository.save(employeeEntity);
        return employeeMapper.toEmployeeDTO(employeeEntity);
    }

    @Transactional
    public EmployeeDTO deleteEmployeeById(UUID id) throws NotFoundInDBException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден сотрудник по id = "+ id));
        UUID employeeId = employee.getId();
        employeeRepository.deleteById(employeeId);
        return employeeMapper.toEmployeeDTO(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeListByFirstname(String firstname) throws NotFoundInDBException {
        List<Employee> employees = employeeRepository.searchByFirstname(firstname).orElseThrow(() -> new NotFoundInDBException("Не найден сотрудник по имени = "+ firstname));
        return employeeMapper.toEmployeeDTOs(employees);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeListByLastname(String lastname) throws NotFoundInDBException {
        List<Employee> employees = employeeRepository.searchByLastname(lastname).orElseThrow(() -> new NotFoundInDBException("Не найден сотрудник по фамилии = "+ lastname));
        return employeeMapper.toEmployeeDTOs(employees);
    }
    
}
