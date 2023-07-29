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
public class Artist {

    //DTO constructor
    public Artist(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @Column(
            name = "ArtistId",
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
            mappedBy = "artist",
            fetch = FetchType.EAGER
    )
    private List<Album> albumList;
}
