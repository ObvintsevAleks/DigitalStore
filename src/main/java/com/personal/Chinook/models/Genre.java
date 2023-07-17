package com.personal.Chinook.models;

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

    @Id
    @Column(name = "GenreId", nullable = false)
    private Integer genreId;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Track> trackList;
}
