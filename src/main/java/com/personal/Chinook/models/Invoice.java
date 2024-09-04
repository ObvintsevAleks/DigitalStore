package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "invoiceLines")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "invoiceLines")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InvoiceId", nullable = false)
    private UUID id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "InvoiceDate")
    @CreatedDate
    private ZonedDateTime invoiceDate = ZonedDateTime.now();

    @Column(name = "BillingAddress", length = 70, nullable = false)
    private String billingAddress;

    @Column(name = "BillingCity", length = 120)
    private String billingCity;

    @Column(name = "BillingState", length = 120)
    private String billingState;

    @Column(name = "BillingCountry", length = 120)
    private String billingCountry;

    @Column(name = "BillingPostalCode", length = 120)
    private String billingPostalCode;

    @Column(name = "Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "CustomerId",
            referencedColumnName = "CustomerId",
            foreignKey = @ForeignKey(name = "FK_InvoiceCustomerId"),
            nullable = false
    )
    private Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<InvoiceLine> invoiceLines;
}
