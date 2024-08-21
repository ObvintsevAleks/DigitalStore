package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Schema(description = "Работник")
@Getter
@AllArgsConstructor
public class EmployeeSaveDTO {

    @Schema(description = "Имя работника", example = "Biba")
    @JsonProperty(value = "firstName",  required = true)
    private String firstName;

    @Schema(description = "Фамилия работника", example = "Boba")
    @JsonProperty(value = "lastName",  required = true)
    private String lastName;

    @Schema(description = "title", example = "tipa title")
    @JsonProperty(value = "title")
    private String title;

    @Schema(description = "Менеджер, кто нанял работника")
    @JsonProperty(value = "reportsTo")
    private Integer reportsTo;

    @Schema(description = "День рождения")
    @JsonProperty(value = "birthDate",  required = true)
    private Timestamp birthDate;

    @Schema(description = "Дата найма")
    @JsonProperty(value = "hireDate")
    private Timestamp hireDate;

    @Schema(description = "Адрес")
    @JsonProperty(value = "address")
    private String address;

    @Schema(description = "Город")
    @JsonProperty(value = "city")
    private String city;

    @Schema(description = "Регион/штат")
    @JsonProperty(value = "state")
    private String state;

    @Schema(description = "Страна")
    @JsonProperty(value = "country")
    private String country;

    @Schema(description = "Почтовый код")
    @JsonProperty(value = "country")
    private String postalCode;

    @Schema(description = "Номер телефона")
    @JsonProperty(value = "phone")
    private String phone;

    @Schema(description = "Факс")
    @JsonProperty(value = "fax")
    private String fax;

    @Schema(description = "Email")
    @JsonProperty(value = "email")
    private String email;
}
