package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class InvoiceLineDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Invoice ID")
    private Integer invoiceId;

    @JsonProperty("Track ID")
    private Integer trackId;

    @JsonProperty("Unit Price")
    private BigDecimal unitPrice;

    @JsonProperty("Quantity")
    private Integer quantity;
}
