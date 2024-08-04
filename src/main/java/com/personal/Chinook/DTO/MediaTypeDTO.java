package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.UUID;

@Schema(description = "Медиа-тип")
@Getter
@AllArgsConstructor
public class MediaTypeDTO {

    @Schema(description = "Идентификатор медиа-типа", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "Id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Название медиа-типа", example = "mp4")
    @JsonProperty(value = "Name", defaultValue = "mp4", required = true)
    private String name;

    @Schema(description = "Дата создания медиа-типа", example = "2019-08-06T16:30:00Z")
    @JsonProperty(value = "createdAt", defaultValue = "2019-08-06T16:30:00Z", required = true)
    private ZonedDateTime createdAt;

}
