package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.EmployeeDTO;
import com.personal.Chinook.DTO.EmployeeSaveDTO;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    EmployeeDTO toEmployeeDTO(Employee employee);

    List<EmployeeDTO> toEmployeeDTOs(List<Employee> employees);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "customers", ignore = true)
    Employee toEmployee(EmployeeSaveDTO employee);

    @Mapping(target = "id", ignore = true) //bc we dont want to override id
    @Mapping(target = "customers", ignore = true)
    @Mapping(target = "manager", ignore = true)
    void updateEmployee(@MappingTarget Employee employee, EmployeeDTO artistDTO);

}
