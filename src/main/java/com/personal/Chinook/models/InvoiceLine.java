package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InvoiceLine {

    @Id
    @Column(name = "InvoiceLineId", nullable = false)
    private Integer invoiceLineId;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "InvoiceId",
            referencedColumnName = "InvoiceId",
            foreignKey = @ForeignKey(name = "FK_InvoiceLineInvoiceId")
    )
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "TrackId",
            referencedColumnName = "TrackId",
            foreignKey = @ForeignKey(name = "FK_InvoiceLineTrackId")
    )
    private Track track;
}
