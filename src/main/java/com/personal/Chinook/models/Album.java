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
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AlbumId", nullable = false)
    private UUID id;

    @Column(name = "Title", length = 160, nullable = false)
    private String title;

    @Column(name = "albumType", nullable = false)
    @Enumerated(EnumType.STRING)
    private AlbumType albumType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ArtistId",
            referencedColumnName = "ArtistId",
            foreignKey = @ForeignKey(name = "FK_AlbumArtistId"),
            nullable = false
    )
    private Artist artist;

    @JsonIgnore
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Track> tracks;
}
