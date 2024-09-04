package com.personal.Chinook.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InvoiceLineId", nullable = false)
    private UUID id;

    @Column(name = "UnitPrice", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "InvoiceId",
            referencedColumnName = "InvoiceId",
            foreignKey = @ForeignKey(name = "FK_InvoiceLineInvoiceId"),
            nullable = false
    )
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "TrackId",
            referencedColumnName = "TrackId",
            foreignKey = @ForeignKey(name = "FK_InvoiceLineTrackId"),
            nullable = false
    )
    private Track track;
}
