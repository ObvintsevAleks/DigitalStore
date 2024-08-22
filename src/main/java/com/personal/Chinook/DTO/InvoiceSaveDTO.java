package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Schema(description = "Инвойс")
@Getter
@AllArgsConstructor
public class InvoiceSaveDTO {

    @Schema(description = "Дата инвойса")
    @JsonProperty(value ="invoiceDate",  required = true)
    private Timestamp invoiceDate;

    @Schema(description = "Адрес оплаты")
    @JsonProperty(value = "billingAddress")
    private String billingAddress;

    @Schema(description = "Город адреса оплаты")
    @JsonProperty(value = "billingCity")
    private String billingCity;

    @Schema(description = "Штат/регион оплаты")
    @JsonProperty(value = "billingState")
    private String billingState;

    @Schema(description = "Страна оплаты")
    @JsonProperty(value = "billingCountry")
    private String billingCountry;

    @Schema(description = "Почтовый код оплаты")
    @JsonProperty(value = "billingPostalCode")
    private String billingPostalCode;

    @Schema(description = "Итоговая сумма")
    @JsonProperty(value = "total")
    private BigDecimal total;

    @Schema(description = "Клиент", example = "Biba")
    @JsonProperty(value = "customerDto",  required = true)
    private CustomerDTO customerDto;

}
