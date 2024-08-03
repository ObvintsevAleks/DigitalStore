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
public class Album {

    @Id
    @Column(name = "AlbumId", nullable = false)
    private Integer id;

    @Column(name = "Title", length = 160, nullable = false)
    private String title;

    @Column(name = "IsSingle", nullable = false)
    private Boolean isSingle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ArtistId",
            referencedColumnName = "ArtistId",
            foreignKey = @ForeignKey(name = "FK_AlbumArtistId")
    )
    private Artist artist;

    @JsonIgnore
    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Track> tracks;
}
