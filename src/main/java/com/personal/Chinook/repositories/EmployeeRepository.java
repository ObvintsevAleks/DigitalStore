package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("select e from Employee e where e.firstName like %:firstname% ")
    List<Employee> searchByFirstname(@Param("firstname") String firstname);

    @Query("select e from Employee e where e.lastName like %:lastname% ")
    List<Employee> searchByLastname(@Param("lastname") String lastname);

}
