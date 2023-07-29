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
public class Genre {

    //DTO constructor
    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(
            name = "GenreId",
            nullable = false
    )
    private Integer id;

    @Column(
            name = "Name",
            nullable = false,
            length = 120
    )
    private String name;

    @JsonIgnore
    @OneToMany(
            mappedBy = "genre",
            fetch = FetchType.LAZY
    )
    private List<Track> trackList;
}
