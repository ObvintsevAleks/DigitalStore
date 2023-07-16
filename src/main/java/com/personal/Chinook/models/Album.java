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
public class Album {

    @Id
    @Column(name = "AlbumId", nullable = false)
    private Integer albumId;

    @Column(name = "Title", length = 160, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ArtistId", referencedColumnName = "ArtistId",foreignKey = @ForeignKey(name = "FK_AlbumArtistId"))
    private Artist artist;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Track> trackList;
}
