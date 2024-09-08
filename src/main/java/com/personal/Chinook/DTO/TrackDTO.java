package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Композиция")
@Getter
@AllArgsConstructor
public class TrackDTO {

    @Schema(description = "Идентификатор композиции", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Название композиции", example = "First memory type")
    @JsonProperty(value = "name",  required = true)
    private String name;

    @Schema(description = "Автор композиции", example = "ASAP")
    @JsonProperty("author")
    private String author;

    @Schema(description = "Длина композиции в миллисекундах", example = "3000")
    @JsonProperty("milliseconds")
    private Integer milliseconds;

    @Schema(description = "Размер композиции в байтах", example = "30000")
    @JsonProperty("bytes")
    private Integer bytes;

    @Schema(description = "Цена", example = "2.41")
    @JsonProperty(value =  "unitPrice", required = true)
    private BigDecimal unitPrice;

    @Schema(description = "Альбом")
    @JsonProperty(value = "album", required = true)
    private AlbumDTO album;

    @Schema(description = "Медиа-тип")
    @JsonProperty(value = "mediaType", required = true)
    private MediaTypeDTO mediaType;

    @Schema(description = "Жанр")
    @JsonProperty(value = "genre", required = true)
    private GenreDTO genre;

    @JsonIgnore
    @Schema(description = "Идентификатор invoice-line")
    @JsonProperty("tracks")
    private List<InvoiceLineDTO> invoiceLines;

}
