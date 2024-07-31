package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlbumDTO {

    @Schema(description = "идентиификатор", example = "1")
    @JsonProperty("ID")
    private Integer id;

    @Schema(description = "заголовок", example = "1")
    @JsonProperty("Album Title")
    private String title;

    @Schema(description = "id artists", example = "1")
    @JsonProperty("Artist ID")
    private Integer artistId;

    @Schema(description = "Id track Lists", example = "1")
    @JsonProperty("Track ID List")
    private List<Integer> trackList;
}
