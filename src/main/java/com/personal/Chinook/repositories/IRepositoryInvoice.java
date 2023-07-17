package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryInvoice extends JpaRepository<Invoice, Integer> {
}
