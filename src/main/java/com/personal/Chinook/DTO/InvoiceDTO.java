package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(description = "Инвойс")
@Getter
@AllArgsConstructor
public class InvoiceDTO {

    @Schema(description = "Идентификатор инвойса", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Дата инвойса", example = "2019-08-06T16:30:00Z")
    @JsonProperty(value = "invoiceDate", defaultValue = "2019-08-06T16:30:00Z", required = true)
    private ZonedDateTime invoiceDate;

    @Schema(description = "Адрес оплаты", example = "Tumen, Pushkin st. 22, flat 10")
    @JsonProperty(value = "billingAddress", defaultValue = "Tumen, Pushkin st. 22, flat 10")
    private String billingAddress;

    @Schema(description = "Город адреса оплаты", example = "Tumen")
    @JsonProperty(value = "billingCity", defaultValue = "Tumen")
    private String billingCity;

    @Schema(description = "Штат/регион оплаты", example = "Tumen region")
    @JsonProperty(value = "billingState", defaultValue = "Tumen region")
    private String billingState;

    @Schema(description = "Страна оплаты", example = "Russia")
    @JsonProperty(value = "billingCountry", defaultValue = "Russia")
    private String billingCountry;

    @Schema(description = "Почтовый код оплаты", example = "6440111")
    @JsonProperty(value = "billingPostalCode", defaultValue = "6440111")
    private String billingPostalCode;

    @Schema(description = "Итоговая сумма", example = "10.10")
    @JsonProperty(value = "total", defaultValue = "10.10")
    private BigDecimal total;

    @Schema(description = "Клиент")
    @JsonProperty(value = "customerDto", required = true)
    private CustomerDTO customerDto;
}
