package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Schema(description = "InvoiceLine")
@Getter
@AllArgsConstructor
public class InvoiceLineSaveDTO {

    @JsonProperty("invoiceDto")
    private InvoiceDTO invoiceDto;

    @JsonProperty("trackDto")
    private TrackDTO trackDto;

    @Schema(description = "Цена юнита")
    @JsonProperty(value =  "unitPrice",  required = true)
    private BigDecimal unitPrice;

    @Schema(description = "Количество", example = "2")
    @JsonProperty(value =  "quantity",  required = true)
    private Integer quantity;

}
