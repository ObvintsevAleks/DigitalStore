package com.personal.Chinook.repositories;

import com.personal.Chinook.models.InvoiceLine;
import com.personal.Chinook.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, UUID> {
    @Query("select il from InvoiceLine il where il.track.id = :trackId")
    Optional<List<InvoiceLine>> findByTrackId(@Param("trackId") UUID trackId);

    @Query("select il from InvoiceLine il where il.invoice.id = :invoiceId")
    Optional<List<InvoiceLine>> findByInvoiceId(@Param("invoiceId") UUID invoiceId);
}
