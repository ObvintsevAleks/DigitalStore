package com.personal.Chinook.mapper;

import com.personal.Chinook.DTO.CustomerDTO;
import com.personal.Chinook.DTO.CustomerSaveDTO;
import com.personal.Chinook.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerDTO toCustomerDTO(Customer customer);

    List<CustomerDTO> toCustomerDTOs(List<Customer> customers);

    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerSaveDTO customerSaveDTO);

    @Mapping(target = "id", ignore = true)
    void updateCustomer(@MappingTarget Customer customer, CustomerDTO customerDTO);

}
