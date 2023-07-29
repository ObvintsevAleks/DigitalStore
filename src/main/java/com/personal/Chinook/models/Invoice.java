package com.personal.Chinook.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Invoice {

    @Id
    @Column(
            name = "InvoiceId",
            nullable = false
    )
    private Integer id;

    @Column(
            name = "InvoiceDate",
            nullable = false
    )
    private Timestamp invoiceDate;

    @Column(
            name = "BillingAddress",
            length = 70
    )
    private String billingAddress;

    @Column(
            name = "BillingCity",
            length = 40
    )
    private String billingCity;

    @Column(
            name = "BillingState",
            length = 40
    )
    private String billingState;

    @Column(
            name = "BillingCountry",
            length = 40
    )
    private String billingCountry;

    @Column(
            name = "BillingPostalCode",
            length = 10
    )
    private String billingPostalCode;

    @Column(
            name = "Total",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal total;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "customerId",
            referencedColumnName = "customerId",
            foreignKey = @ForeignKey(name = "FK_InvoiceCustomerId")
    )
    private Customer customer;

    @OneToMany(
            mappedBy = "invoice",
            fetch = FetchType.LAZY
    )
    private List<InvoiceLine> invoiceLineList;
}
