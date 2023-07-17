package com.personal.Chinook.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Employee {

    @Id
    @Column(name = "EmployeeId", nullable = false)
    private Integer employeeId;

    @Column(name = "FirstName", nullable = false, length = 20)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 20)
    private String lastName;

    @Column(name = "Title", length = 30)
    private String title;

    @Column(name = "BirthDate")
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

    @Column(name = "Email", length = 60)
    private String email;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Customer> customerList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportsTo", referencedColumnName = "EmployeeId", foreignKey = @ForeignKey(name = "FK_EmployeeReportsTo"))
    private Employee manager;
}
