package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EmployeeSaveDTO {

    @Schema(description = "Имя", example = "Biba")
    @JsonProperty(value = "firstName", defaultValue = "Biba", required = true)
    private String firstName;

    @Schema(description = "Фамилия", example = "Boba")
    @JsonProperty(value = "lastName", defaultValue = "Biba", required = true)
    private String lastName;

    @Schema(description = "Позиция", example = "SHOP_ASSISTANT",
            allowableValues = {"DIRECTOR", "MANAGER", "SHOP_ASSISTANT", "TRAINEE"})
    @JsonProperty(value = "position", defaultValue = "SHOP_ASSISTANT", required = true)
    private String position;

    @Schema(description = "День рождения", example = "2019-08-06")
    @JsonProperty(value = "birthDate", defaultValue = "2019-08-06", required = true)
    private LocalDate birthDate;

    @Schema(description = "Дата найма", example = "2019-08-06")
    @JsonProperty(value = "hireDate", defaultValue = "2019-08-06", required = true)
    private LocalDate hireDate;

    @Schema(description = "Адрес", example = "ул Пушкина, д. 125, кв. 22")
    @JsonProperty(value = "address", defaultValue = "ул Пушкина, д. 125, кв. 22", required = true)
    private String address;

    @Schema(description = "Город", example = "Tumen")
    @JsonProperty(value = "city", defaultValue = "Tumen")
    private String city;

    @Schema(description = "Регион/штат", example = "Tumen region")
    @JsonProperty(value = "state", defaultValue = "Tumen region")
    private String state;

    @Schema(description = "Страна", example = "Russia")
    @JsonProperty(value = "country", defaultValue = "Russia")
    private String country;

    @Schema(description = "Почтовый код", example = "6440111")
    @JsonProperty(value = "postalCode", defaultValue = "6440111")
    private String postalCode;

    @Schema(description = "Номер телефона", example = "+1999221211")
    @JsonProperty(value = "phone", defaultValue = "+1999221211")
    private String phone;

    @Schema(description = "Факс")
    @JsonProperty(value = "fax")
    private String fax;

    @Schema(description = "Email", example = "somemail@mail.com")
    @JsonProperty(value = "email", defaultValue = "somemail@mail.com", required = true)
    private String email;

}
