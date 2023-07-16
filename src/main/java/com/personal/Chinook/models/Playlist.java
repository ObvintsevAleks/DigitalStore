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
public class Playlist {

    @Id
    @Column(name = "PlaylistId", nullable = false)
    private Integer playlistId;

    @Column(name = "Name", length = 120)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PlaylistTrack",
            joinColumns = @JoinColumn(name = "PlaylistId", foreignKey = @ForeignKey(name = "FK_PLT_PlaylistId")),
            inverseJoinColumns = @JoinColumn(name = "TrackId", foreignKey = @ForeignKey(name = "FK_PLT_TrackId"))
    )
    private List<Track> trackList;
}
