package com.personal.DigitalStore.repositories;

import com.personal.DigitalStore.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("select e from Customer e where e.firstName like %:firstname% ")
    Optional<List<Customer>> searchByFirstname(@Param("firstname") String firstname);

    @Query("select e from Customer e where e.lastName like %:lastname% ")
    Optional<List<Customer>> searchByLastname(@Param("lastname") String lastname);

}
