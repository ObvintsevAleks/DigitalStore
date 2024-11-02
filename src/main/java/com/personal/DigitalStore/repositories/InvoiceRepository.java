package com.personal.DigitalStore.repositories;

import com.personal.DigitalStore.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Query("select i from Invoice i where i.customer.id = :customerId")
    Optional<List<Invoice>> findByCustomerId(@Param("customerId") UUID customerId);

    @Query("select i from Invoice i where i.employee.id = :employeeId")
    Optional<List<Invoice>> findByEmployeeId(@Param("employeeId") UUID employeeId);
}
