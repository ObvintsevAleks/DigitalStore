package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.Chinook.models.GenreDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GenreSaveDTO {

    @Schema(description = "Название жанра", example = "pop music")
    @JsonProperty(value = "Name", defaultValue = "pop music", required = true)
    private String name;

    @Schema(description = "Направленность жанра")
    @JsonProperty(value = "GenreDirection", defaultValue = "POPULAR")
    private GenreDirection genreDirection;

    @JsonIgnore
    @Schema(description = "Идентификатор треков")
    @JsonProperty("Track ID List")
    private List<TrackDTO> tracks;
}
