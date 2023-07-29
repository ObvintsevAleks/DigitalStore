package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MediaTypeDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Name")
    private String name;
}
