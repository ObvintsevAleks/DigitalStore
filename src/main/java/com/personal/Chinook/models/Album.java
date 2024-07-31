package com.personal.Chinook.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(exclude = "trackList")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "trackList")
public class Album {

    @Id
    @Column(name = "AlbumId", nullable = false)
    private Integer id;

    @Column(name = "Title", length = 160, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ArtistId",
            referencedColumnName = "ArtistId",
            foreignKey = @ForeignKey(name = "FK_AlbumArtistId")
    )
    private Artist artist;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Track> trackList;
}
