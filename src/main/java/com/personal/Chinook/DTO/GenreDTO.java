package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.Chinook.models.GenreDirection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Schema(description = "Жанр")
@Getter
@AllArgsConstructor
public class GenreDTO {

    @Schema(description = "Идентификатор жанра", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "Id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Название жанра", example = "cloud rap")
    @JsonProperty(value = "Name", defaultValue = "cloud rap", required = true)
    private String name;

    @Schema(description = "Направленность жанра")
    @JsonProperty(value = "GenreDirection", defaultValue = "POPULAR")
    private GenreDirection genreDirection;

    @JsonIgnore
    @Schema(description = "Идентификатор треков")
    @JsonProperty("Track ID List")
    private List<TrackDTO> tracks;

}
