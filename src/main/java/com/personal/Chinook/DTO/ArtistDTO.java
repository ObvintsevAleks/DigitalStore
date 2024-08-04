package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Schema(description = "Артист")
@Getter
@AllArgsConstructor
public class ArtistDTO {

    @Schema(description = "Идентификатор артиста", example = "8e262c04-a090-11e8-98d0-529269fb1459")
    @JsonProperty("ArtistId")
    private UUID id;

    @Schema(description = "Имя артиста", example = "Luke")
    @JsonProperty("Name")
    private String name;

    @Schema(description = "Фамилия артиста", example = "Barrras")
    @JsonProperty("Surname")
    private String surname;
}
