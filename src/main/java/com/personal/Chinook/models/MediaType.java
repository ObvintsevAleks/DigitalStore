package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MediaType {

    //DTO constructor
    public MediaType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(
            name = "MediaTypeId",
            nullable = false
    )
    private Integer id;

    @Column(
            name = "Name",
            length = 120
    )
    private String name;

    @JsonIgnore
    @OneToMany(
            mappedBy = "mediaType",
            fetch = FetchType.LAZY
    )
    private List<Track> trackList;
}
