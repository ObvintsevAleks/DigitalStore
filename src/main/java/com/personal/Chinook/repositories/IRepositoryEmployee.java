package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryEmployee extends JpaRepository<Employee, Integer> {
}
