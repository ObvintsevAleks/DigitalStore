package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.Chinook.models.enumpack.AlbumType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
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

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "albumType", nullable = false)
    @Enumerated(EnumType.STRING)
    private AlbumType albumType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private ZonedDateTime createdAt;

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
