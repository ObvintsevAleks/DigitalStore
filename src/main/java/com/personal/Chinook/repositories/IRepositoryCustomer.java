package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CustomerRepo")
public interface IRepositoryCustomer extends JpaRepository<Customer, Integer> {
}
