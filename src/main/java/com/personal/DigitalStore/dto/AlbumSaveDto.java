package com.personal.DigitalStore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personal.DigitalStore.models.enumpack.AlbumType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class AlbumSaveDto {

    @Schema(description = "Заголовок альбома", example = "The first album")
    @JsonProperty(value = "title", defaultValue = "The first album", required = true)
    private String title;

    @Schema(description = "Тип альбома", example = "SINGLE")
    @JsonProperty(value = "albumType", defaultValue = "SINGLE", required = true)
    private AlbumType albumType;

    @Schema(description = "Дата создания альбома", example = "2019-08-06T16:30:00Z")
    @JsonProperty(value = "createdAt", defaultValue = "2019-08-06T16:30:00Z", required = true)
    private ZonedDateTime createdAt;

    @Schema(description = "Артист")
    @JsonProperty(value = "artist", required = true)
    private ArtistDTO artist;

}
