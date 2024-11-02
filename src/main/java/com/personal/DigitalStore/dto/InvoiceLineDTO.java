package com.personal.DigitalStore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "InvoiceLine")
@Getter
@AllArgsConstructor
public class InvoiceLineDTO {

    @Schema(description = "Идентификатор InvoiceLine", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Цена юнита", example = "10.10")
    @JsonProperty(value = "unitPrice", defaultValue = "10.10", required = true)
    private BigDecimal unitPrice;

    @Schema(description = "Количество", example = "2")
    @JsonProperty(value = "quantity", defaultValue = "2", required = true)
    private Integer quantity;

    @Schema(description = "invoice")
    @JsonProperty(value = "invoice", required = true)
    private InvoiceDTO invoice;

    @Schema(description = "track")
    @JsonProperty(value = "track", required = true)
    private TrackDTO track;

}
