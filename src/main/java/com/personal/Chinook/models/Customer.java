package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "invoices")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "invoices")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CustomerId", nullable = false)
    private UUID id;

    @Column(name = "FirstName",length = 120, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 120, nullable = false)
    private String lastName;

    @Column(name = "Company", length = 80)
    private String company;

    @Column(name = "Address", length = 70)
    private String address;

    @Column(name = "City", length = 40)
    private String city;

    @Column(name = "State", length = 40)
    private String state;

    @Column(name = "Country", length = 40)
    private String country;

    @Column(name = "PostalCode", length = 10)
    private String postalCode;

    @Column(name = "Phone", length = 24)
    private String phone;

    @Column(name = "Fax", length = 24)
    private String fax;

    @Column(name = "Email", nullable = false, length = 120)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "EmployeeId",
            referencedColumnName = "EmployeeId",
            foreignKey = @ForeignKey(name = "FK_CustomerEmployeeId")
    )
    private Employee employee;

    // jsonignore property to not display it as part of request/response body
    // relationship left untouched for jpql benefit for queries
    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
