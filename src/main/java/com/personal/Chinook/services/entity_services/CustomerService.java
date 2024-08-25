package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.CustomerDTO;
import com.personal.Chinook.DTO.CustomerSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.CustomerMapper;
import com.personal.Chinook.models.Customer;
import com.personal.Chinook.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerDTO createCustomer(CustomerSaveDTO customerSaveDTO) {
        Customer customer = customerMapper.toCustomer(customerSaveDTO);
        customerRepository.save(customer);
        return customerMapper.toCustomerDTO(customer);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(UUID id) throws NotFoundInDBException {
        Customer Customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден покупатель по id = "+ id));
        return customerMapper.toCustomerDTO(Customer);
    }

    @Transactional
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws NotFoundInDBException {
        Customer customerEntity = customerRepository.findById(customerDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден покупатель по id = "+ customerDTO.getId()));
        if (customerMapper.toCustomerDTO(customerEntity).equals(customerDTO)) {
            return customerMapper.toCustomerDTO(customerEntity);
        }
        customerMapper.updateCustomer(customerEntity, customerDTO);
        customerRepository.save(customerEntity);
        return customerMapper.toCustomerDTO(customerEntity);
    }

    @Transactional
    public CustomerDTO deleteCustomerById(UUID id) throws NotFoundInDBException {
        Customer Customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден покупатель по id = "+ id));
        UUID CustomerId = Customer.getId();
        customerRepository.deleteById(CustomerId);
        return customerMapper.toCustomerDTO(Customer);
    }


    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomerListByFirstname(String firstname) throws NotFoundInDBException {
        List<Customer> Customers = customerRepository.searchByFirstname(firstname);
        if (Customers.isEmpty()) {
            throw new NotFoundInDBException("2");
        }
        return customerMapper.toCustomerDTOs(Customers);
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> getCustomerListByLastname(String lastname) throws NotFoundInDBException {
        List<Customer> Customers = customerRepository.searchByLastname(lastname);
        if (Customers.isEmpty()) {
            throw new NotFoundInDBException("2");
        }
        return customerMapper.toCustomerDTOs(Customers);
    }


}
