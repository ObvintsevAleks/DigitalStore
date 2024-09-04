package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class InvoiceLineSaveDTO {

    @Schema(description = "invoice")
    @JsonProperty(value = "invoice", required = true)
    private InvoiceDTO invoice;

    @Schema(description = "track")
    @JsonProperty(value = "track", required = true)
    private TrackDTO track;

    @Schema(description = "Цена юнита", example = "10.10")
    @JsonProperty(value = "unitPrice", defaultValue = "10.10", required = true)
    private BigDecimal unitPrice;

    @Schema(description = "Количество", example = "2")
    @JsonProperty(value = "quantity", defaultValue = "2", required = true)
    private Integer quantity;

}
