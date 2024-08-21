package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "customers")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "customers")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EmployeeId", nullable = false)
    private UUID id;

    @Column(name = "FirstName", nullable = false, length = 120)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 120)
    private String lastName;

    @Column(name = "Title", length = 30)
    private String title;

    @Column(name = "BirthDate", nullable = false)
    private Timestamp birthDate;

    @Column(name = "HireDate")
    private Timestamp hireDate;

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

    @Column(name = "Email", length = 120)
    private String email;

    // jsonignore property to not display it as part of request/response body
    // relationship left untouched for jpql benefit for queries
    @JsonIgnore
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Customer> customers;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ReportsTo",
            referencedColumnName = "EmployeeId",
            foreignKey = @ForeignKey(name = "FK_EmployeeReportsTo")
    )
    private Employee manager;
}
