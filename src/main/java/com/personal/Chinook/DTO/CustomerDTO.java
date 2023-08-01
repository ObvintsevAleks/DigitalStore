package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {
    @JsonProperty("ID")
    private final Integer id;

    @JsonProperty("First Name")
    private final String firstName;

    @JsonProperty("Last Name")
    private final String lastName;

    @JsonProperty("Company")
    private final String company;

    @JsonProperty("Address")
    private final String address;

    @JsonProperty("City")
    private final String city;

    @JsonProperty("State")
    private final String state;

    @JsonProperty("Country")
    private final String country;

    @JsonProperty("Postal Code")
    private final String postalCode;

    @JsonProperty("Phone")
    private final String phone;

    @JsonProperty("Fax")
    private final String fax;

    @JsonProperty("Email")
    private final String email;

    @JsonProperty("Support Representative ID")
    private final Integer supportRepId;
}
