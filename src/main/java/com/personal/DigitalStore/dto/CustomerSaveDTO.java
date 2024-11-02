package com.personal.DigitalStore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CustomerSaveDTO {

    @Schema(description = "Имя", example = "Biba")
    @JsonProperty(value = "firstName", defaultValue = "Biba", required = true)
    private final String firstName;

    @Schema(description = "Фамилия", example = "Boba")
    @JsonProperty(value = "lastName", defaultValue = "Boba", required = true)
    private final String lastName;

    @Schema(description = "День рождения", example = "2019-08-06")
    @JsonProperty(value = "birthDate", defaultValue = "2019-08-06")
    private LocalDate birthDate;

    @Schema(description = "Адрес", example = "Pushkin street 22")
    @JsonProperty(value = "address", defaultValue = "Pushkin street 22", required = true)
    private final String address;

    @Schema(description = "Город", example = "Tumen")
    @JsonProperty(value = "city", defaultValue = "Tumen")
    private final String city;

    @Schema(description = "Регион/штат", example = "Tumen region")
    @JsonProperty(value = "state", defaultValue = "Tumen region")
    private final String state;

    @Schema(description = "Страна", example = "Russia")
    @JsonProperty(value = "country", defaultValue = "Russia")
    private final String country;

    @Schema(description = "Почтовый код", example = "6440111")
    @JsonProperty(value = "postalCode", defaultValue = "6440111")
    private final String postalCode;

    @Schema(description = "Номер телефона", example = "+1999221211")
    @JsonProperty(value = "phone", defaultValue = "+1999221211")
    private final String phone;

    @Schema(description = "Факс")
    @JsonProperty(value = "fax")
    private final String fax;

    @Schema(description = "Email", example = "somemail@mail.com")
    @JsonProperty(value = "email", defaultValue = "somemail@mail.com", required = true)
    private final String email;

}
