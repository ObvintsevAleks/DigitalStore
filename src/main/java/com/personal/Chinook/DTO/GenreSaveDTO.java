package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.Chinook.models.enumpack.GenreDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class GenreSaveDTO {

    @Schema(description = "Название жанра", example = "cloud rap")
    @JsonProperty(value = "name", defaultValue = "cloud rap", required = true)
    private String name;

    @Schema(description = "Дата создания жанра", example = "1985-10-07")
    @JsonProperty(value = "createdAt", required = true, defaultValue = "1985-10-07")
    private LocalDate createdAt;

    @Schema(description = "Направленность жанра")
    @JsonProperty(value = "genreDirection", defaultValue = "POPULAR")
    private GenreDirection genreDirection;

    @JsonIgnore
    @Schema(description = "Идентификатор треков")
    @JsonProperty("tracks")
    private List<TrackDTO> tracks;
}
