package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.Chinook.models.AlbumType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlbumSaveDto {


    @Schema(description = "Заголовок альбома", example = "1")
    @JsonProperty(value = "title", defaultValue = "cl123ap", required = true)
    private String title;

    @Schema(description = "Тип альбома")
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
