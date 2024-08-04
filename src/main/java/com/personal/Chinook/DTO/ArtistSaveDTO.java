package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ArtistSaveDTO {

    @Schema(description = "Имя артиста", example = "Luke")
    @JsonProperty("Name")
    private String name;

    @Schema(description = "Фамилия артиста", example = "Barrras")
    @JsonProperty("Surname")
    private String surname;
}
