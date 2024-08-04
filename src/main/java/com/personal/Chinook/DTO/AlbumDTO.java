package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.Chinook.models.AlbumType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Schema(description = "Альбом")
@Getter
@AllArgsConstructor
public class AlbumDTO {

    @Schema(description = "Идентификатор альбома", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty(value = "Id", defaultValue = "8e262c04-a090-11e8-98d0-529269fb1459", required = true)
    private UUID id;

    @Schema(description = "Заголовок альбома", example = "1")
    @JsonProperty(value = "title",  required = true)
    private String title;

    @Schema(description = "Тип альбома", example = "SINGLE")
    @JsonProperty(value ="albumType", defaultValue = "SINGLE", required = true)
    private AlbumType albumType;

    @Schema(description = "DTO артиста")
    @JsonProperty(value = "Artist", required = true)
    private ArtistDTO artist;

    @JsonIgnore
    @Schema(description = "Идентификатор треков")
    @JsonProperty("Track ID List")
    private List<TrackDTO> tracks;
}
