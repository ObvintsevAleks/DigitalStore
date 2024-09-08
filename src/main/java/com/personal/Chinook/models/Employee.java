package com.personal.Chinook.models;

import com.personal.Chinook.models.enumpack.Position;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EmployeeId", nullable = false)
    private UUID id;

    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @Column(name = "LastName", nullable = false)
    private String lastName;

    @Column(name = "Position", nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "BirthDate", nullable = false)
    @CreatedDate
    private LocalDate birthDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "HireDate", nullable = false)
    @CreatedDate
    private LocalDate hireDate;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "Country")
    private String country;

    @Column(name = "PostalCode")
    private String postalCode;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Fax")
    private String fax;

    @Column(name = "Email", nullable = false)
    private String email;

}
