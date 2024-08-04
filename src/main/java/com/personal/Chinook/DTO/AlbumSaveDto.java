package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Schema(description = "Является ли синглом", example = "1")
    @JsonProperty(value ="IsSingle", defaultValue = "true", required = true)
    private Boolean isSingle;

    @Schema(description = "DTO артиста")
    @JsonProperty("Artist ID")
    private ArtistDTO artist;

    @JsonIgnore
    @Schema(description = "Идентификатор треков")
    @JsonProperty("Track ID List")
    private List<TrackDTO> tracks;

}
