package com.personal.DigitalStore.repositories;

import com.personal.DigitalStore.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("select e from Employee e where e.firstName like %:firstname% ")
    Optional<List<Employee>> searchByFirstname(@Param("firstname") String firstname);

    @Query("select e from Employee e where e.lastName like %:lastname% ")
    Optional<List<Employee>> searchByLastname(@Param("lastname") String lastname);

}
