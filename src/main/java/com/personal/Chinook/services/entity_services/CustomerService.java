package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.CustomerDTO;
import com.personal.Chinook.models.Customer;
import com.personal.Chinook.services.common_query_functions.IDBCrud;
import com.personal.Chinook.services.common_query_functions.ILocationQueries;
import com.personal.Chinook.services.common_query_functions.INameQuery;
import com.personal.Chinook.services.common_query_functions.IPersonalDataQueries;

import java.util.List;
import java.util.Optional;

public class CustomerService implements IDBCrud<Customer, CustomerDTO>,
                                        ILocationQueries<Customer>,
                                        IPersonalDataQueries<Customer>,
                                        INameQuery<Customer>
{
    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Customer persist(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void update(CustomerDTO customerDTO) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Customer> getByCountry(String country) {
        return null;
    }

    @Override
    public List<Customer> getByState(String state) {
        return null;
    }

    @Override
    public List<Customer> getByCity(String city) {
        return null;
    }

    @Override
    public List<Customer> getByAddress(String address) {
        return null;
    }

    @Override
    public List<Customer> getByPostalCode(String postalCode) {
        return null;
    }

    @Override
    public List<Customer> getByName(String name) {
        return null;
    }

    @Override
    public List<Customer> getByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<Customer> getByLastNme(String lastName) {
        return null;
    }

    @Override
    public List<Customer> getByEmail(String email) {
        return null;
    }

    @Override
    public List<Customer> getByFax(String fax) {
        return null;
    }

    @Override
    public List<Customer> getByPhone(String phone) {
        return null;
    }
}
