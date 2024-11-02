package com.personal.DigitalStore.mappers;

import com.personal.DigitalStore.dto.EmployeeDTO;
import com.personal.DigitalStore.dto.EmployeeSaveDTO;
import com.personal.DigitalStore.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeDTO toEmployeeDTO(Employee employee);

    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    Employee toEmployee(EmployeeSaveDTO employee);

    @Mapping(target = "id", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, EmployeeDTO artistDTO);

}
