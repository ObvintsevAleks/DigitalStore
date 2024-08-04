package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "tracks")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tracks")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GenreId", nullable = false)
    private UUID id;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @Column(name = "Area", nullable = false, length = 120)
    private String area;

    @Column(name = "IsOnlyInstrumental", nullable = false)
    private Boolean isOnlyInstrumental;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<Track> tracks;

}
