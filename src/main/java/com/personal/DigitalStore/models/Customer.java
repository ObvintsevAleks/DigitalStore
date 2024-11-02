package com.personal.DigitalStore.models;

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
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String firstName;

    @Column(name = "surname", nullable = false)
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth_date")
    @CreatedDate
    private LocalDate birthDate;

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
