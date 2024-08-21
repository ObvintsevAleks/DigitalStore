package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "Клиент")
@Getter
@AllArgsConstructor
public class CustomerSaveDTO {


    @Schema(description = "Имя клиента", example = "Biba")
    @JsonProperty(value = "firstName",  required = true)
    private final String firstName;

    @Schema(description = "Фамилия клиента", example = "Boba")
    @JsonProperty(value = "lastName",  required = true)
    private final String lastName;

    @Schema(description = "Название компании", example = "Biba and Boba company LTD")
    @JsonProperty(value = "company")
    private final String company;

    @Schema(description = "Адрес")
    @JsonProperty(value = "address")
    private final String address;

    @Schema(description = "Город")
    @JsonProperty(value = "city")
    private final String city;

    @Schema(description = "Регион/штат")
    @JsonProperty(value = "state")
    private final String state;

    @Schema(description = "Страна")
    @JsonProperty(value = "country")
    private final String country;

    @Schema(description = "Почтовый код")
    @JsonProperty(value = "country")
    private final String postalCode;

    @Schema(description = "Номер телефона")
    @JsonProperty(value = "phone")
    private final String phone;

    @Schema(description = "Факс")
    @JsonProperty(value = "fax")
    private final String fax;

    @Schema(description = "Email")
    @JsonProperty(value = "email")
    private final String email;

    @Schema(description = "DTO работника")
    @JsonProperty(value = "employee", required = true)
    private EmployeeDTO employee;

    @JsonIgnore
    @Schema(description = "Идентификатор инвойсов")
    @JsonProperty("Invoice ID List")
    private List<InvoiceDTO> tracks;

}
