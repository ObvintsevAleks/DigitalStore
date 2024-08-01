package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(exclude = "tracks")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tracks")
public class Genre {

    @Id
    @Column(name = "GenreId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Track> tracks;

}
