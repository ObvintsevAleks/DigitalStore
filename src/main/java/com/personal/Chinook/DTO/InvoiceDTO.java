package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class InvoiceDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Customer ID")
    private Integer customerId;

    @JsonProperty("Invoice Date")
    private Timestamp invoiceDate;

    @JsonProperty("Billing Address")
    private String billingAddress;

    @JsonProperty("Billing City")
    private String billingCity;

    @JsonProperty("Billing State")
    private String billingState;

    @JsonProperty("Billing Country")
    private String billingCountry;

    @JsonProperty("Billing Postal Code")
    private String billingPostalCode;

    @JsonProperty("Total")
    private BigDecimal total;
}
