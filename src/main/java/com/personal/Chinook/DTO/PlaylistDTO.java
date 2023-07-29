package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class PlaylistDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Track ID List")
    private List<Integer> trackList;
}
