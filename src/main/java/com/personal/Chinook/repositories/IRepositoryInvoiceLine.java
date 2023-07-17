package com.personal.Chinook.repositories;

import com.personal.Chinook.models.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryInvoiceLine extends JpaRepository<InvoiceLine, Integer> {
}
