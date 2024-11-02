package com.personal.DigitalStore.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice_line")
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "invoice_line_id", nullable = false)
    private UUID id;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "invoice_id",
            referencedColumnName = "invoice_id",
            foreignKey = @ForeignKey(name = "fk_invoice_line_invoice_id"),
            nullable = false
    )
    private Invoice invoice;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "track_id",
            referencedColumnName = "track_id",
            foreignKey = @ForeignKey(name = "fk_invoice_line_track_id"),
            nullable = false
    )
    private Track track;
}
