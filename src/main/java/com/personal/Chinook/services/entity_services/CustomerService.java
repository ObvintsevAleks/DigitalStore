package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.CustomerDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Customer;
import com.personal.Chinook.models.Employee;
import com.personal.Chinook.repositories.IRepositoryCustomer;
import com.personal.Chinook.repositories.IRepositoryEmployee;
import com.personal.Chinook.services.common_query_functions.IDBCrud;
import com.personal.Chinook.services.common_query_functions.ILocationQueries;
import com.personal.Chinook.services.common_query_functions.INameQuery;
import com.personal.Chinook.services.common_query_functions.IPersonalDataQueries;
import com.personal.Chinook.services.validations.ValidationsUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements IDBCrud<Customer, CustomerDTO>,
                                        ILocationQueries<Customer>,
                                        IPersonalDataQueries<Customer>,
                                        INameQuery<Customer>
{
    private final IRepositoryCustomer repoCustomer;
    private final IRepositoryEmployee repoEmployee;

    @Autowired
    public CustomerService(
            @Qualifier("CustomerRepo") IRepositoryCustomer customerRepo,
            @Qualifier("EmployeeRepo") IRepositoryEmployee employeeRepo
    ) {
        this.repoCustomer = customerRepo;
        this.repoEmployee = employeeRepo;
    }

    @Override
    public List<Customer> getAll() {

        return repoCustomer.findAll();
    }

    @Override
    public Optional<Customer> getById(Integer id) {
        if(id == null)
            throw new InvalidFieldException("ERROR, ID field not present");

        return repoCustomer.findById(id);
    }

    @Override
    public Customer persist(CustomerDTO customerDTO) {
        //CustomerDTO is a data carrier class, containing only the necessary DB table input values
        //ValidationsUtility is a non-initializable class with public static utility methods for validations

        ValidationsUtility.checkNullFields(customerDTO);        //checks for null fields (IF NON-NULLABLE)
        ValidationsUtility.checkNegativeIntegers(customerDTO);  //checks negative numbers (Integers)
        ValidationsUtility.checkStringsContent(customerDTO);    //checks string content (empty/blank, special chars)

        //customer's assigned support red (might be null for customers saved without one initially)
        Employee assignedEmployee = null;

        boolean containsSupportRep = customerDTO.getSupportRepId() != null;
        boolean supportRepExists = false;

        if(containsSupportRep) {
            //tries to overwrite to "true" IF it exists (boolean return value type from repo method)
            supportRepExists = repoEmployee.existsById(customerDTO.getSupportRepId());
        }

        //custom 404 Http error exception handle if assigned support rep does not exist in database
        if(containsSupportRep && !supportRepExists)
                throw new NotFoundInDBException("ERROR, support rep employee doesn't exist in database");

        //contains support rep and exists
        else if(containsSupportRep)
            assignedEmployee = repoEmployee.findById(customerDTO.getSupportRepId()).get();


        return repoCustomer.save(
                new Customer(
                        customerDTO.getId(),
                        customerDTO.getFirstName(),
                        customerDTO.getLastName(),
                        customerDTO.getCompany(),
                        customerDTO.getAddress(),
                        customerDTO.getCity(),
                        customerDTO.getState(),
                        customerDTO.getCountry(),
                        customerDTO.getPostalCode(),
                        customerDTO.getPhone(),
                        customerDTO.getFax(),
                        customerDTO.getEmail(),
                        assignedEmployee,
                        null
                )
        );
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        //if it exists, for updating
        boolean customerExists = repoCustomer.existsById(customerDTO.getId());

        if(customerExists)
            persist(customerDTO);
        else
            throw new NotFoundInDBException("ERROR, customer does not exist in database");
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
