package com.personal.DigitalStore.models;

import com.personal.DigitalStore.models.enumpack.Position;
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
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String lastName;

    @Column(name = "position", nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth_date", nullable = false)
    @CreatedDate
    private LocalDate birthDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "hire_date", nullable = false)
    @CreatedDate
    private LocalDate hireDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email", nullable = false)
    private String email;

}
