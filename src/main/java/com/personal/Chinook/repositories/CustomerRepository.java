package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Customer;
import com.personal.Chinook.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("select e from Customer e where e.firstName like %:firstname% ")
    List<Customer> searchByFirstname(@Param("firstname") String firstname);

    @Query("select e from Customer e where e.firstName like %:lastname% ")
    List<Customer> searchByLastname(@Param("lastname") String lastname);

}
