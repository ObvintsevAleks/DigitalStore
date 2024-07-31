package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenreDTO {

    @Schema(description = "идентиификатор", example = "1")
    @JsonProperty("ID")
    private Integer id;

    @Schema(description = "name", example = "1")
    @JsonProperty("Name")
    private String name;
}
