package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class EmployeeDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("First Name")
    private String firstName;

    @JsonProperty("Last Name")
    private String lastName;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Manager ID")
    private Integer reportsTo;

    @JsonProperty("Date of Birth")
    private Timestamp birthDate;

    @JsonProperty("Hire Date")
    private Timestamp hireDate;

    @JsonProperty("Adrress")
    private String address;

    @JsonProperty("City")
    private String city;

    @JsonProperty("State")
    private String state;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Postal Code")
    private String postalCode;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Fax")
    private String fax;

    @JsonProperty("Email")
    private String email;
}
