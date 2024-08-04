package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "Жанр")
@Getter
@AllArgsConstructor
public class GenreDTO {

    @Schema(description = "Идентификатор жанра", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty("ID")
    private UUID id;

    @Schema(description = "Название жанра", example = "cloud rap")
    @JsonProperty("Name")
    private String name;

    @Schema(description = "Направленность жанра", example = "Популярная музыка, академическая музыка и тд")
    @JsonProperty("Area")
    private String area;

    @Schema(description = "Используется ли только инструментал", example = "true")
    @JsonProperty("IsOnlyInstrumental")
    private Boolean isOnlyInstrumental;
}
