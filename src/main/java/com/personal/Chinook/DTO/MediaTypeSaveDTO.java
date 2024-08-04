package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class MediaTypeSaveDTO {

    @Schema(description = "Название mediaType", example = "mp4")
    @JsonProperty(value = "Name", defaultValue = "mp4", required = true)
    private String name;

    @Schema(description = "Дата создания mediaType", example = "2019-08-06T16:30:00Z")
    @JsonProperty(value = "createdAt", defaultValue = "2019-08-06T16:30:00Z", required = true)
    private ZonedDateTime createdAt;

}
