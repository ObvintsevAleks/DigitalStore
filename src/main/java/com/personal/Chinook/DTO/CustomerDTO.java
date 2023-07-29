package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("First Name")
    private String firstName;

    @JsonProperty("Last Name")
    private String lastName;

    @JsonProperty("Company")
    private String company;

    @JsonProperty("Address")
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

    @JsonProperty("Support Representative ID")
    private Integer supportRepId;
}
