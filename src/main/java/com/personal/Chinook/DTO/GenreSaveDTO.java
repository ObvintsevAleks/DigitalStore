package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenreSaveDTO {

    @Schema(description = "Название жанра", example = "cloud rap")
    @JsonProperty("Name")
    private String name;

    @Schema(description = "Направленность жанра", example = "Популярная музыка, академическая музыка и тд")
    @JsonProperty("Area")
    private String area;

    @Schema(description = "Используется только инструментал", example = "true")
    @JsonProperty("IsOnlyInstrumental")
    private Boolean isOnlyInstrumental;
}
