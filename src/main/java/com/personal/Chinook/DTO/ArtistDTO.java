package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "Artists dto")
@Getter
@AllArgsConstructor
public class ArtistDTO {

    @Schema(description = "идентиификатор", example = "1")
    @JsonProperty("ID")
    private Integer id;

    @Schema(description = "наименоване", example = "некий вася")
    @JsonProperty("Name")
    private String name;
}
