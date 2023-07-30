package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Customer {

    @Id
    @Column(
            name = "CustomerId",
            nullable = false
    )
    private Integer customerId;

    @Column(
            name = "FirstName",
            nullable = false,
            length = 40
    )
    private String firstName;

    @Column(
            name = "LastName",
            nullable = false,
            length = 20
    )
    private String lastName;

    @Column(
            name = "Company",
            length = 80
    )
    private String company;

    @Column(
            name = "Address",
            length = 70
    )
    private String address;

    @Column(
            name = "City",
            length = 40
    )
    private String city;

    @Column(
            name = "State",
            length = 40
    )
    private String state;

    @Column(
            name = "Country",
            length = 40
    )
    private String country;

    @Column(
            name = "PostalCode",
            length = 10
    )
    private String postalCode;

    @Column(
            name = "Phone",
            length = 24
    )
    private String phone;

    @Column(
            name = "Fax",
            length = 24
    )
    private String fax;

    @Column(
            name = "Email",
            nullable = false,
            length = 60
    )
    private String email;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "SupportRepId",
            referencedColumnName = "EmployeeId",
            foreignKey = @ForeignKey(name = "FK_CustomerSupportRepId")
    )
    private Employee employee;

    // jsonignore property to not display it as part of request/response body
    // relationship left untouched for jpql benefit for queries
    @JsonIgnore
    @OneToMany(
            mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    private List<Invoice> invoiceList;
}
