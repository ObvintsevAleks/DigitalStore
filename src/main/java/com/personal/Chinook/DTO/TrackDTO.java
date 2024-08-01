package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TrackDTO {

    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Album ID")
    private Integer albumId;

    @JsonProperty("Media Type ID")
    private Integer mediaTypeId;

    @JsonProperty("Genre ID")
    private Integer genreId;

    @JsonProperty("Composer")
    private String composer;

    @JsonProperty("Milliseconds")
    private Integer milliseconds;

    @JsonProperty("Bytes")
    private Integer bytes;

    @JsonProperty("Unit Price")
    private BigDecimal unitPrice;
}
