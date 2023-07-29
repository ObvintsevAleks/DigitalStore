package com.personal.Chinook.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlbumDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("Album Title")
    private String title;

    @JsonProperty("Artist ID")
    private Integer artistId;

    @JsonProperty("Track ID List")
    private List<Integer> trackList;
}
